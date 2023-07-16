package com.reto.plazoleta.application.mapper.responsemapper;

import com.reto.plazoleta.application.dto.request.OrderWithASingleDishDto;
import com.reto.plazoleta.application.dto.response.*;
import com.reto.plazoleta.domain.model.RestaurantModel;
import com.reto.plazoleta.domain.model.dishs.*;
import com.reto.plazoleta.domain.model.OrderDishModel;
import com.reto.plazoleta.domain.model.OrderModel;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.Collections;
import java.util.stream.Collectors;

@UtilityClass
public class OrderMapper {

    private static final String TYPE_DISH_MEAT = "Carne";
    private static final String TYPE_DISH_SOUP = "Sopas";
    private static final String TYPE_DISH_DESSERT = "Postre";

    private static final String TYPE_DISH_ICE_CREAM_DESSERT = "Helado";
    private static final String TYPE_DISH_FLAN_DESSERT = "Flan";

    public static ResponseOrderDto orderModelToOrderTakenResponseDto(OrderModel orderModel) {
        return ResponseOrderDto.builder()
                .idOrder(orderModel.getIdOrder())
                .idUserCustomer(orderModel.getIdUserCustomer())
                .date(orderModel.getDate())
                .status(orderModel.getStatus().toString())
                .idChef(orderModel.getEmployeeRestaurantModel().getIdUserEmployee())
                .dishes(orderModel.getOrdersDishesModel().stream()
                        .map(OrderMapper::orderDishToDishTypeOrdered)
                        .collect(Collectors.toList()))
                .build();
    }

    private ResponseTypeDishOrderDto orderDishToDishTypeOrdered(OrderDishModel orderDishModel) {
        ResponseTypeDishOrderDto dishTypeOrderedResponseDto = dataModelToDishTypeOrderedResponseDto(orderDishModel.getDishModel());
        return ResponseTypeDishOrderDto.builder()
                .idDish(orderDishModel.getDishModel().getIdDish())
                .typeDish(dishTypeOrderedResponseDto != null ? dishTypeOrderedResponseDto.getTypeDish() : null)
                .dessertType(dishTypeOrderedResponseDto != null ? dishTypeOrderedResponseDto.getDessertType() : null)
                .accompaniment(dishTypeOrderedResponseDto != null ? dishTypeOrderedResponseDto.getAccompaniment() : null)
                .Flavor(dishTypeOrderedResponseDto != null ? dishTypeOrderedResponseDto.getFlavor() : null)
                .grams(dishTypeOrderedResponseDto != null ? dishTypeOrderedResponseDto.getGrams() : null)
                .build();
    }

    private ResponseTypeDishOrderDto dataModelToDishTypeOrderedResponseDto(DishModel dish) {
        if (dish instanceof Meat) {
            return buildMeatDishTypeOrderedResponse((Meat) dish);
        } else if (dish instanceof Soup) {
            return buildSoupDishTypeOrderedResponse((Soup) dish);
        } else if (dish instanceof FlanModel) {
            return buildFlanDessertTypeOrderedResponse((FlanModel) dish);
        } else if (dish instanceof IceCreamModel) {
            return buildIceCreamDessertTypeOrderedResponse((IceCreamModel) dish);
        }
        return null;
    }

    private ResponseTypeDishOrderDto buildMeatDishTypeOrderedResponse(Meat meatDish) {
        ResponseTypeDishOrderDto dishTypeOrderedResponse = new ResponseTypeDishOrderDto();
        dishTypeOrderedResponse.setTypeDish(TYPE_DISH_MEAT);
        dishTypeOrderedResponse.setGrams(meatDish.getGrams());
        return dishTypeOrderedResponse;
    }

    private ResponseTypeDishOrderDto buildSoupDishTypeOrderedResponse(Soup soupDish) {
        ResponseTypeDishOrderDto dishTypeOrderedResponse = new ResponseTypeDishOrderDto();
        dishTypeOrderedResponse.setTypeDish(TYPE_DISH_SOUP);
        dishTypeOrderedResponse.setAccompaniment(soupDish.getAccompaniment());
        return dishTypeOrderedResponse;
    }

    private ResponseTypeDishOrderDto buildFlanDessertTypeOrderedResponse(FlanModel flanDessertDish) {
        ResponseTypeDishOrderDto dishTypeOrderedResponse = new ResponseTypeDishOrderDto();
        dishTypeOrderedResponse.setTypeDish(TYPE_DISH_FLAN_DESSERT);
        dishTypeOrderedResponse.setAccompaniment(flanDessertDish.getTopping());
        return dishTypeOrderedResponse;
    }

    private ResponseTypeDishOrderDto buildIceCreamDessertTypeOrderedResponse(IceCreamModel iceCreamDessertDish) {
        ResponseTypeDishOrderDto dishTypeOrderedResponse = new ResponseTypeDishOrderDto();
        dishTypeOrderedResponse.setTypeDish(TYPE_DISH_ICE_CREAM_DESSERT);
        dishTypeOrderedResponse.setFlavor(iceCreamDessertDish.getFlavor());
        return dishTypeOrderedResponse;
    }
    private PendingDishResponseDto dishModelToPendingDishResponseDto(DishModel dish) {
        if (dish instanceof Meat) {
            return buildMeatDishToPendingDishResponseDto((Meat) dish);
        } else if (dish instanceof Soup) {
            return buildSoupDishToPendingDishResponseDto((Soup) dish);
        } else if (dish instanceof FlanModel) {
            return buildFlanDessertToPendingDishResponseDto((FlanModel) dish);
        } else if (dish instanceof IceCreamModel) {
            return buildIceCreamDessertToPendingDishResponseDto((IceCreamModel) dish);
        }
        return null;
    }

    private PendingDishResponseDto buildMeatDishToPendingDishResponseDto(Meat meatDish) {
        return PendingDishResponseDto.builder()
                .typeDish(TYPE_DISH_MEAT)
                .grams(meatDish.getGrams())
                .build();
    }

    private PendingDishResponseDto buildSoupDishToPendingDishResponseDto(Soup soupDish) {
        return PendingDishResponseDto.builder()
                .typeDish(TYPE_DISH_SOUP)
                .accompaniment(soupDish.getAccompaniment())
                .build();
    }

    private PendingDishResponseDto buildFlanDessertToPendingDishResponseDto(FlanModel flanDessertDish) {
        ResponseTypeDishOrderDto dishTypeOrderedResponse = new ResponseTypeDishOrderDto();
        dishTypeOrderedResponse.setDessertType(TYPE_DISH_FLAN_DESSERT);
        dishTypeOrderedResponse.setAccompaniment(flanDessertDish.getTopping());
        return PendingDishResponseDto.builder()
                .typeDish(TYPE_DISH_DESSERT)
                .typeDessert(TYPE_DISH_FLAN_DESSERT)
                .accompaniment(flanDessertDish.getTopping())
                .build();
    }

    private PendingDishResponseDto buildIceCreamDessertToPendingDishResponseDto(IceCreamModel iceCreamDessertDish) {
        return PendingDishResponseDto.builder()
                .typeDish(TYPE_DISH_DESSERT)
                .typeDessert(TYPE_DISH_FLAN_DESSERT)
                .flavor(iceCreamDessertDish.getFlavor())
                .build();
    }


    private PendingDishResponseDto orderDishModelToPendingDishResponseDto(OrderDishModel orderDish) {
        PendingDishResponseDto pendingDishResponseDto = dishModelToPendingDishResponseDto(orderDish.getDishModel());
        return PendingDishResponseDto.builder()
                .idDish(orderDish.getDishModel().getIdDish())
                .typeDish(pendingDishResponseDto != null ? pendingDishResponseDto.getTypeDish() : null)
                .typeDessert(pendingDishResponseDto != null ? pendingDishResponseDto.getTypeDessert() : null)
                .accompaniment(pendingDishResponseDto != null ? pendingDishResponseDto.getAccompaniment() : null)
                .flavor(pendingDishResponseDto != null ? pendingDishResponseDto.getFlavor() : null)
                .grams(pendingDishResponseDto != null ? pendingDishResponseDto.getGrams() : null)
                .build();
    }

    public static PendingOrdersNotOrganizedResponseDto orderModelToPendingOrderResponseDto(OrderModel order) {
        return PendingOrdersNotOrganizedResponseDto.builder()
                .idOrder(order.getIdOrder())
                .idUserCustomer(order.getIdUserCustomer())
                .date(order.getDate())
                .status(order.getStatus().toString())
                .dishes(order.getOrdersDishesModel().stream()
                        .map(OrderMapper::orderDishModelToPendingDishResponseDto)
                        .collect(Collectors.toList()))
                .build();
    }

    private RestaurantModel longAsIdRestaurantToRestaurantModel(Long idRestaurant) {
        return new RestaurantModel(
                idRestaurant,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }
    private OrderDishModel dishFromOrderRequestDtoToOrderDishModel(OrderWithASingleDishDto orderWithASingleDishDto) {
        return new OrderDishModel(
                null,
                null,
                singleDishOrderRequestDtoToDishModel(orderWithASingleDishDto),
                1
        );
    }

    public static OrderModel singleDishOrderRequestDtoToOrderModel(OrderWithASingleDishDto orderWithASingleDishDto, Long idRestaurant) {
        return new OrderModel(
                null,
                null,
                LocalDate.now(),
                null,
                null,
                longAsIdRestaurantToRestaurantModel(idRestaurant),
                Collections.singletonList(dishFromOrderRequestDtoToOrderDishModel(orderWithASingleDishDto))
        );
    }

    private DishModel singleDishOrderRequestDtoToDishModel(OrderWithASingleDishDto orderWithASingleDishDto) {
        String typeDish = orderWithASingleDishDto.getTypeDish();
        if (typeDish.equalsIgnoreCase(TYPE_DISH_MEAT)) {
            return buildSingleDishOrderRequestDtoToMeatDish(orderWithASingleDishDto);
        } else if (typeDish.equalsIgnoreCase(TYPE_DISH_SOUP)) {
            return buildSingleDishOrderRequestDtoToSoupDish(orderWithASingleDishDto);
        } else if (typeDish.equalsIgnoreCase(TYPE_DISH_DESSERT)) {
            String typeDessert = orderWithASingleDishDto.getTypeDessert();
            if (typeDessert.equalsIgnoreCase(TYPE_DISH_FLAN_DESSERT)) {
                return buildSingleDishOrderRequestDtoToFlanDessertDish(orderWithASingleDishDto);
            } else if (typeDessert.equalsIgnoreCase(TYPE_DISH_ICE_CREAM_DESSERT)) {
                return buildSingleDishOrderRequestDtoToIceCreamDessertDish(orderWithASingleDishDto);
            }
        }
        return null;
    }

    private Meat buildSingleDishOrderRequestDtoToMeatDish(OrderWithASingleDishDto singleMeatDishRequest) {
        return new Meat(singleMeatDishRequest.getIdDish(), singleMeatDishRequest.getGrams());
    }

    private Soup buildSingleDishOrderRequestDtoToSoupDish(OrderWithASingleDishDto singleSoupDishRequest) {
        return new Soup(singleSoupDishRequest.getIdDish(), singleSoupDishRequest.getAccompaniment());
    }

    private FlanModel buildSingleDishOrderRequestDtoToFlanDessertDish(OrderWithASingleDishDto singleFlanDessertDishRequest) {
        return new FlanModel(singleFlanDessertDishRequest.getIdDish(), singleFlanDessertDishRequest.getAccompaniment());
    }

    private IceCreamModel buildSingleDishOrderRequestDtoToIceCreamDessertDish(OrderWithASingleDishDto singleIceCreamDessertDishRequest) {
        return new IceCreamModel(singleIceCreamDessertDishRequest.getIdDish(), singleIceCreamDessertDishRequest.getFlavor());
    }

    public static SingleDishOrderResponseDto orderModelToSingleDishOrderResponseDto(OrderModel orderModel) {
        return SingleDishOrderResponseDto.builder()
                .idOrder(orderModel.getIdOrder())
                .build();
    }
}
