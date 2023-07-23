package com.reto.plazoleta.domain.usecase;

import com.reto.plazoleta.domain.api.ICustomerServicePort;
import com.reto.plazoleta.domain.exception.*;
import com.reto.plazoleta.domain.gateways.IUserGateway;
import com.reto.plazoleta.domain.model.*;
import com.reto.plazoleta.domain.model.dishs.ComparatorDishModel;
import com.reto.plazoleta.domain.model.dishs.DishModel;
import com.reto.plazoleta.domain.model.dishs.FlanModel;
import com.reto.plazoleta.domain.model.dishs.IceCreamModel;
import com.reto.plazoleta.domain.model.dishs.Meat;
import com.reto.plazoleta.domain.model.dishs.Soup;
import com.reto.plazoleta.domain.spi.*;
import com.reto.plazoleta.infraestructure.configuration.security.jwt.JwtProvider;
import com.reto.plazoleta.infraestructure.drivenadapter.entity.StatusOrder;
import com.reto.plazoleta.infraestructure.drivenadapter.gateways.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class CustomerUseCase implements ICustomerServicePort {

    private static final String TYPE_DISH_MEAT = "CARNE";
    private static final String TYPE_DISH_SOUP = "SOPAS";
    private static final String TYPE_DISH_DESSERT = "POSTRE";
    private static final String TYPE_DESSERT_FLAN = "FLAN";
    private static final String TYPE_DESSERT_ICE_CREAM = "HELADO";
    private final IOrderPersistencePort orderPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IDishPersistencePort dishPersistencePort;
    private final IUserGateway userGateway;
    private final JwtProvider jwtProvider;
    private final IOrderDishPersistencePort orderDishPersistencePort;
    private final IToken token;
    private Map<String, PriorityQueue<OrderDishModel>> dishes;

    public CustomerUseCase(IOrderPersistencePort orderPersistencePort, IRestaurantPersistencePort restaurantPersistencePort,
                           IDishPersistencePort dishPersistencePort, IUserGateway userGateway,
                           JwtProvider jwtProvider, IOrderDishPersistencePort orderDishPersistencePort, IToken token) {
        this.orderPersistencePort = orderPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.dishPersistencePort = dishPersistencePort;
        this.userGateway = userGateway;
        this.jwtProvider = jwtProvider;
        this.orderDishPersistencePort = orderDishPersistencePort;
        this.token = token;
    }

    @Override
    public OrderModel saveOrder(OrderModel orderModelRequest, String tokenWithPrefixBearer) {
        String emailFromUserAuthenticated = getEmailFromUserAuthenticatedByTokenWithPrefixBearer(tokenWithPrefixBearer);
        final User userCustomerFound = getUserByEmail(emailFromUserAuthenticated, tokenWithPrefixBearer);
        validateRestaurant(orderModelRequest.getRestaurantModel().getIdRestaurant());
        checkIfTheCustomerHasAnOrderInProcess(orderModelRequest.getRestaurantModel().getIdRestaurant(), userCustomerFound.getIdUser());

        List<OrderDishModel> ordersDishesModelsRequest = orderModelRequest.getOrdersDishesModel();

        orderModelRequest.setIdUserCustomer(userCustomerFound.getIdUser());
        orderModelRequest.setDate(LocalDate.now());
        orderModelRequest.setStatus(StatusOrder.PENDIENTE);
        orderModelRequest.setOrdersDishesModel(new ArrayList<>());
        OrderModel orderModelSaved = this.orderPersistencePort.saveOrder(orderModelRequest);
        orderModelSaved.setOrdersDishesModel(ordersDishesModelsRequest);
        List<OrderDishModel> addOrderAndAmountOfDish = createOrdersDishesComplete(orderModelSaved);
        List<OrderDishModel> orderDishModelsSaved = this.orderDishPersistencePort.saveAllOrdersDishes(addOrderAndAmountOfDish);
        orderModelSaved.setOrdersDishesModel(orderDishModelsSaved);
        return orderModelSaved;
    }

    private void validateStatusFromOrderAndIfBelongTheUserAuthenticated(OrderModel orderModelToValidate, Long idUserAuthenticated) {
        if (orderModelToValidate == null) {
            throw new OrderNotExistsException("The order not exist");
        } else if (!orderModelToValidate.getIdUserCustomer().equals(idUserAuthenticated)) {
            throw new OrderNotExistsException("The order does not belong to the user");
        } else if (!orderModelToValidate.getStatus().equals(StatusOrder.PENDIENTE)) {
            throw new OrderInProcessException("Lo sentimos, tu pedido ya está en preparación y no puede cancelarse");
        }
    }

    @Override
    public Page<DishModel> getAllDishesActivePaginatedFromARestaurantOrderByCategoryAscending(Integer numberPage, Integer sizeItems, Long idRestaurant) {
        validateRestaurant(idRestaurant);
        Page<DishModel> dishesPaginatedAndOrderByCategory = this.dishPersistencePort
                .getAllDishesActiveOfARestaurantOrderByCategoryAscending(PageRequest.of(numberPage, sizeItems), idRestaurant);
        checkIfListIsEmpty(dishesPaginatedAndOrderByCategory.isEmpty());
        return dishesPaginatedAndOrderByCategory;
    }

    @Override
    public OrderModel addSingleDishOrder(OrderModel orderRequest) {
        validateIfRestaurantExists(orderRequest.getRestaurantModel().getIdRestaurant());
        String tokenWithPrefixBearer = this.token.getTokenWithPrefixBearerFromUserAuthenticated();
        User customer = getUserByEmail(getEmailFromToken(tokenWithPrefixBearer), tokenWithPrefixBearer);
        orderRequest.setStatus(StatusOrder.PENDIENTE);
        orderRequest.setIdUserCustomer(customer.getIdUser());
        orderRequest.setOrdersDishesModel(getOrdersDishesOrganizedByPriority(orderRequest));
        return this.orderPersistencePort.saveOrderAndOrdersDishes(orderRequest);
    }

    private void validateIfRestaurantExists(Long idRestaurant) {
        if (!this.restaurantPersistencePort.existRestaurantById(idRestaurant)) {
            throw new ObjectNotFoundException("");
        }
    }

    private List<OrderDishModel> getOrdersDishesOrganizedByPriority(OrderModel orderRequest) {
        initializeMapOfTheDishes();
        for (OrderDishModel orderDishModel : orderRequest.getOrdersDishesModel()) {
            DishModel dishCompleteData = this.dishPersistencePort.findById(orderDishModel.getDishModel().getIdDish());
            validateIfDishExists(dishCompleteData);
            orderDishModel.setDishModel(getDishType(orderDishModel.getDishModel(), dishCompleteData));
            orderDishModel.setOrderModel(orderRequest);
            addDish(orderDishModel);
        }
        return dishes.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private void addDish(OrderDishModel orderDish) {
        String dishType = orderDish.getDishModel().getCategoryModel().getName();
        PriorityQueue<OrderDishModel> ordersDishesOfADishType = this.dishes
                .computeIfAbsent(dishType, value ->
                        new PriorityQueue<>(new ComparatorDishModel().reversed()));
        ordersDishesOfADishType.offer(orderDish);
        this.dishes.put(dishType, ordersDishesOfADishType);
    }

    private void initializeMapOfTheDishes() {
        dishes = new LinkedHashMap<>();
        dishes.put(TYPE_DISH_MEAT, new PriorityQueue<>(new ComparatorDishModel().reversed()));
        dishes.put(TYPE_DISH_SOUP, new PriorityQueue<>(new ComparatorDishModel().reversed()));
        dishes.put(TYPE_DISH_DESSERT, new PriorityQueue<>(new ComparatorDishModel().reversed()));
    }

    private Meat buildMeatDish(DishModel dishTypeMeat, DishModel dishWithDataComplete) {
        dishTypeMeat.updateAllDataFromAllFieldsFromDishModel(dishWithDataComplete);
        return ((Meat) dishTypeMeat);
    }

    private DishModel getDishType(DishModel searchDishType, DishModel dishWithDataComplete) {
        CategoryModel categoryModelType = dishWithDataComplete.getCategoryModel();
        String dishType = categoryModelType.getName();
        if (searchDishType instanceof Meat && dishType.equalsIgnoreCase(TYPE_DISH_MEAT)) {
            return validateGramsFromMeat(buildMeatDish(searchDishType, dishWithDataComplete));
        } else if (searchDishType instanceof Soup && dishType.equalsIgnoreCase(TYPE_DISH_SOUP)) {
            return buildSoup(searchDishType, dishWithDataComplete);
        } else if (searchDishType instanceof FlanModel && dishType.equalsIgnoreCase(TYPE_DESSERT_FLAN)) {
            return buildFlanDessert(searchDishType, dishWithDataComplete);
        } else if (searchDishType instanceof IceCreamModel && dishType.equalsIgnoreCase(TYPE_DESSERT_ICE_CREAM)) {
            return buildIceCreamDessertDish(searchDishType, dishWithDataComplete);
        }
        throw new DishNotExistsException("");
    }
    private IceCreamModel buildIceCreamDessertDish(DishModel dishTypeIceCreamDessertDish, DishModel dishWithDataComplete) {
        dishTypeIceCreamDessertDish.updateAllDataFromAllFieldsFromDishModel(dishWithDataComplete);
        return (IceCreamModel) dishTypeIceCreamDessertDish;
    }

    private FlanModel buildFlanDessert(DishModel dishTypeFlanDessertDish, DishModel dishWithDataComplete) {
        dishTypeFlanDessertDish.updateAllDataFromAllFieldsFromDishModel(dishWithDataComplete);
        return (FlanModel) dishTypeFlanDessertDish;
    }

    private Soup buildSoup(DishModel dishTypeSoupDish, DishModel dishWithDataComplete) {
        dishTypeSoupDish.updateAllDataFromAllFieldsFromDishModel(dishWithDataComplete);
        return (Soup) dishTypeSoupDish;
    }

    private Meat validateGramsFromMeat(Meat meat) {
        if (meat.getGrams() == null) {
            throw new EmptyFieldsException("");
        }
        else if ( !(meat.getGrams() >= 250 && meat.getGrams() <= 750) ) {
            throw new DishNotExistsException("");
        }
        return meat;
    }
    private void validateIfDishExists(DishModel dishToValidate) {
        if (dishToValidate == null) {
            throw new DishNotExistsException("");
        }
    }
    private String getEmailFromToken(String tokenWithPrefixBearer) {
        return this.token.getEmailFromToken(tokenWithPrefixBearer);
    }

    private void checkIfListIsEmpty(boolean isTheListEmpty) {
        if (isTheListEmpty)
            throw new NoDataFoundException();
    }

    @Override
    public OrderModel orderCanceled(Long idOrder, String tokenWithPrefixBearer) {
        String emailFromUserAuthenticated = getEmailFromUserAuthenticatedByTokenWithPrefixBearer(tokenWithPrefixBearer);
        final User userCustomerAuthenticated = getUserByEmail(emailFromUserAuthenticated, tokenWithPrefixBearer);
        OrderModel orderModelToChangeStatusToCanceled = this.orderPersistencePort.findByIdOrder(idOrder);
        validateStatusFromOrderAndIfBelongTheUserAuthenticated(orderModelToChangeStatusToCanceled, userCustomerAuthenticated.getIdUser());
        orderModelToChangeStatusToCanceled.setStatus(StatusOrder.CANCELADO);
        return this.orderPersistencePort.saveOrder(orderModelToChangeStatusToCanceled);
    }

    private String getEmailFromUserAuthenticatedByTokenWithPrefixBearer(String tokenWithPrefixBearer) {
        return this.jwtProvider.getAuthentication(tokenWithPrefixBearer.replace("Bearer ", "")).getName();
    }

    private User getUserByEmail(String email, String tokenWithPrefixBearer) {
        return this.userGateway.getUserByEmailInTheToken(email, tokenWithPrefixBearer);
    }

    private void validateRestaurant(Long idRestaurant) {
        final RestaurantModel restaurantFoundModel = this.restaurantPersistencePort.findByIdRestaurant(idRestaurant);
        if (restaurantFoundModel == null)
            throw new ObjectNotFoundException("The restaurant in the order does not exist");
    }

    private void checkIfTheCustomerHasAnOrderInProcess(Long idRestaurant, Long idUserCustomer) {
        final List<OrderModel> listOfOrdersFromUserFromSameRestaurant = this.orderPersistencePort.findByIdUserCustomerAndIdRestaurant(
                        idUserCustomer, idRestaurant).stream()
                .filter(order -> !order.getStatus().equals(StatusOrder.CANCELADO) && !order.getStatus().equals(StatusOrder.ENTREGADO))
                .collect(Collectors.toList());
        if (!listOfOrdersFromUserFromSameRestaurant.isEmpty())
            throw new CustomerHasAOrderInProcessException("The customer user has an order in process");
    }

    private List<OrderDishModel> createOrdersDishesComplete(OrderModel orderModel) {
        List<OrderDishModel> ordersDishesModelToSave = new ArrayList<>();
        for (OrderDishModel orderDishModel : orderModel.getOrdersDishesModel()) {
            DishModel dishFoundWithValueInAllItsFields = this.dishPersistencePort.findById(orderDishModel.getDishModel().getIdDish());
            if (dishFoundWithValueInAllItsFields == null) {
                throw new DishNotExistsException("The dish does not exist");
            } else if (dishFoundWithValueInAllItsFields.getRestaurantModel().getIdRestaurant().equals(orderModel.getRestaurantModel().getIdRestaurant())) {
                OrderDishModel orderDishModelToSave = new OrderDishModel();
                orderDishModelToSave.setOrderModel(orderModel);
                orderDishModelToSave.setDishModel(dishFoundWithValueInAllItsFields);
                orderDishModelToSave.setAmount(orderDishModel.getAmount());
                ordersDishesModelToSave.add(orderDishModelToSave);
            }
        }
        return ordersDishesModelToSave;
    }

    @Override
    public OrderModel addOrderWithMultipleDishesType(OrderModel orderWithMultipleDishes) {
        validateIfRestaurantExists(orderWithMultipleDishes.getRestaurantModel().getIdRestaurant());
        String tokenWithPrefixBearer = this.token.getTokenWithPrefixBearerFromUserAuthenticated();
        User customer = getUserByEmail(getEmailFromToken(tokenWithPrefixBearer), tokenWithPrefixBearer);
        orderWithMultipleDishes.setStatus(StatusOrder.PENDIENTE);
        orderWithMultipleDishes.setIdUserCustomer(customer.getIdUser());
        List<OrderDishModel> orderDishesToSave = getOrdersDishesOrganizedByPriority(orderWithMultipleDishes);
        orderWithMultipleDishes.setOrdersDishesModel(orderDishesToSave);
        return this.orderPersistencePort.saveOrder(orderWithMultipleDishes);
    }
}
