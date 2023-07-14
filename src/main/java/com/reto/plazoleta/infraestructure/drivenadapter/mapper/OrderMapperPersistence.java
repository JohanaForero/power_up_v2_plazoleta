package com.reto.plazoleta.infraestructure.drivenadapter.mapper;

import com.reto.plazoleta.domain.model.CategoryModel;
import com.reto.plazoleta.domain.model.EmployeeRestaurantModel;
import com.reto.plazoleta.domain.model.OrderDishModel;
import com.reto.plazoleta.domain.model.OrderModel;
import com.reto.plazoleta.domain.model.RestaurantModel;
import com.reto.plazoleta.domain.model.dishs.*;
import com.reto.plazoleta.infraestructure.drivenadapter.entity.*;
import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.stream.Collectors;

@UtilityClass
public class OrderMapperPersistence {

    public static OrderModel convertOrderEntityToOrderModel(OrderEntity orderEntity) {
        if (orderEntity.getOrdersDishesEntity() == null) {
            orderEntity.setOrdersDishesEntity(Collections.emptyList());
        }
        return new OrderModel(
                orderEntity.getIdOrder(),
                orderEntity.getIdUserCustomer(),
                orderEntity.getDate(),
                orderEntity.getStatus(),
                convertEntityToEmployeeRestaurantModel(orderEntity.getEmployeeRestaurantEntity()),
                convertEntityToRestaurantModel(orderEntity.getRestaurantEntity()),
                orderEntity.getOrdersDishesEntity()
                        .stream()
                        .map(OrderMapperPersistence::convertOrderDishEntityToOrderDishModel)
                        .collect(Collectors.toList())
        );
    }

    private EmployeeRestaurantModel convertEntityToEmployeeRestaurantModel(EmployeeRestaurantEntity employeeRestaurantEntity) {
        if (employeeRestaurantEntity != null) {
            return new EmployeeRestaurantModel(
                    employeeRestaurantEntity.getIdRestaurantEmployee(),
                    employeeRestaurantEntity.getIdEmployee(),
                    employeeRestaurantEntity.getIdRestaurant()
            );
        }
        return null;
    }

    private RestaurantModel convertEntityToRestaurantModel(RestaurantEntity restaurantEntity) {
        return new RestaurantModel(
                restaurantEntity.getIdRestaurant(),
                restaurantEntity.getName(),
                restaurantEntity.getAddress(),
                restaurantEntity.getPhone(),
                restaurantEntity.getUrlLogo(),
                restaurantEntity.getNit(),
                restaurantEntity.getIdOwner()
        );
    }

    private OrderDishModel convertOrderDishEntityToOrderDishModel(OrderDishEntity orderDishEntity) {
        return new OrderDishModel(
                orderDishEntity.getIdOrderDish(),
                mapOrderEntityToOrderModelWithoutListOrdersDishes(orderDishEntity.getOrderEntity()),
                convertDishEntityToDishModel(orderDishEntity.getDishEntity(), orderDishEntity),
                orderDishEntity.getAmount()
        );
    }

    private DishModel convertDishEntityToDishModel(DishEntity dishEntity, OrderDishEntity orderDishEntity) {
        String dishType = dishEntity.getCategoryEntity().getName().toString();
        if (dishType.equalsIgnoreCase(TypeDish.CARNE.toString())) {
            return convertToMeatDishModel(dishEntity, orderDishEntity.getGrams());
        } else if (dishType.equalsIgnoreCase(TypeDish.SOPA.toString())) {
            return convertToSoupDishModel(dishEntity, orderDishEntity.getAccompaniment());
        } else if (dishType.equalsIgnoreCase(TypeDish.FLAN.toString())) {
            return convertToFlanDessertDishModel(dishEntity, orderDishEntity.getAccompaniment());
        } else if (dishType.equalsIgnoreCase(TypeDish.HELADO.toString())) {
            return convertToIceCreamDessertDishModel(dishEntity, orderDishEntity.getFlavor());
        } else {
            return null;
        }
    }

    private CategoryModel convertEntityToCategoryModel(CategoryEntity categoryEntity) {
        return new CategoryModel(
                categoryEntity.getIdCategory(),
                categoryEntity.getName().toString(),
                categoryEntity.getDescription()
        );
    }

    private Meat convertToMeatDishModel(DishEntity dishEntity, Integer meatGrams) {
        return new Meat(
                dishEntity.getIdDish(),
                dishEntity.getName(),
                dishEntity.getDescriptionDish(),
                dishEntity.getPrice(),
                dishEntity.getImageDish(),
                dishEntity.getStateDish(),
                convertEntityToRestaurantModel(dishEntity.getRestaurantEntity()),
                convertEntityToCategoryModel(dishEntity.getCategoryEntity()),
                meatGrams
        );
    }

    private Soup convertToSoupDishModel(DishEntity dishEntity, String sideSoup) {
        return new Soup(
                dishEntity.getIdDish(),
                dishEntity.getName(),
                dishEntity.getDescriptionDish(),
                dishEntity.getPrice(),
                dishEntity.getImageDish(),
                dishEntity.getStateDish(),
                convertEntityToRestaurantModel(dishEntity.getRestaurantEntity()),
                convertEntityToCategoryModel(dishEntity.getCategoryEntity()),
                sideSoup
        );
    }

    private FlanModel convertToFlanDessertDishModel(DishEntity dishEntity, String flanTopping) {
        return new FlanModel(
                dishEntity.getIdDish(),
                dishEntity.getName(),
                dishEntity.getDescriptionDish(),
                dishEntity.getPrice(),
                dishEntity.getImageDish(),
                dishEntity.getStateDish(),
                convertEntityToRestaurantModel(dishEntity.getRestaurantEntity()),
                convertEntityToCategoryModel(dishEntity.getCategoryEntity()),
                flanTopping
        );
    }

    private IceCreamModel convertToIceCreamDessertDishModel(DishEntity dishEntity, String iceCreamFlavor) {
        return new IceCreamModel(
                dishEntity.getIdDish(),
                dishEntity.getName(),
                dishEntity.getDescriptionDish(),
                dishEntity.getPrice(),
                dishEntity.getImageDish(),
                dishEntity.getStateDish(),
                convertEntityToRestaurantModel(dishEntity.getRestaurantEntity()),
                convertEntityToCategoryModel(dishEntity.getCategoryEntity()),
                iceCreamFlavor
        );
    }

    private OrderModel mapOrderEntityToOrderModelWithoutListOrdersDishes(OrderEntity orderEntity) {
        return new OrderModel(
                orderEntity.getIdOrder(),
                orderEntity.getIdUserCustomer(),
                orderEntity.getDate(),
                orderEntity.getStatus(),
                convertEntityToEmployeeRestaurantModel(orderEntity.getEmployeeRestaurantEntity()),
                convertEntityToRestaurantModel(orderEntity.getRestaurantEntity())
        );
    }

    public static OrderEntity convertOrderModelToOrderEntity(OrderModel orderModelRequest) {
        return OrderEntity.builder()
                .idOrder(orderModelRequest.getIdOrder())
                .idUserCustomer(orderModelRequest.getIdUserCustomer())
                .date(orderModelRequest.getDate())
                .status(orderModelRequest.getStatus())
                .employeeRestaurantEntity(mapEmployeeRestaurantModelToEmployeeRestaurantEntity(orderModelRequest.getEmployeeRestaurantModel()))
                .restaurantEntity(mapRestaurantModelToRestaurantEntity(orderModelRequest.getRestaurantModel()))
                .ordersDishesEntity(orderModelRequest.getOrdersDishesModel().stream()
                        .map(orderDishModel -> mapOrderDishModelToOrderDishEntity(orderDishModel))
                        .collect(Collectors.toList())
                )
                .build();
    }
    private OrderDishEntity mapOrderDishModelToOrderDishEntity(OrderDishModel orderDishModel) {
        return OrderDishEntity.builder()
                .idOrderDish(orderDishModel.getIdOrderDish())
                .orderEntity(mapOrderModelWithoutListOrdersDishesToOrderEntity(orderDishModel.getOrderModel()))
                .dishEntity(mapDishModelToDishEntity(orderDishModel.getDishModel()))
                .amount(orderDishModel.getAmount())
                .grams(dishModelToIntegerAsGramsOfMeat(orderDishModel.getDishModel()))
                .accompaniment(dishModelToStringAsSoupOrFlanSideDish(orderDishModel.getDishModel()))
                .flavor(dishModelToStringAsIceCreamFlavor(orderDishModel.getDishModel()))
                .build();
    }
    private OrderEntity mapOrderModelWithoutListOrdersDishesToOrderEntity(OrderModel orderModel) {
        return OrderEntity.builder()
                .idOrder(orderModel.getIdOrder())
                .idUserCustomer(orderModel.getIdUserCustomer())
                .date(orderModel.getDate())
                .status(orderModel.getStatus())
                .employeeRestaurantEntity(mapEmployeeRestaurantModelToEmployeeRestaurantEntity(orderModel.getEmployeeRestaurantModel()))
                .restaurantEntity(mapRestaurantModelToRestaurantEntity(orderModel.getRestaurantModel()))
                .ordersDishesEntity(Collections.emptyList())
                .build();
    }

    private CategoryEntity mapCategoryModelToCategoryEntity(CategoryModel categoryModel) {
        return CategoryEntity.builder()
                .idCategory(categoryModel.getIdCategory())
                .description(categoryModel.getDescription())
                .name(TypeDish.valueOf(categoryModel.getName()))
                .build();
    }

    private Integer dishModelToIntegerAsGramsOfMeat(DishModel dish) {
        if (dish instanceof Meat) {
            return ((Meat) dish).getGrams();
        }
        return null;
    }

    private String dishModelToStringAsSoupOrFlanSideDish(DishModel dish) {
        if (dish instanceof Soup) {
            return ((Soup) dish).getAccompaniment();
        } else if (dish instanceof FlanModel) {
            return ((FlanModel) dish).getTopping();
        }
        return null;
    }

    private String dishModelToStringAsIceCreamFlavor(DishModel dish) {
        if (dish instanceof IceCreamModel) {
            return ((IceCreamModel) dish).getFlavor();
        }
        return null;
    }
    private DishEntity mapDishModelToDishEntity(DishModel dishModel) {
        return DishEntity.builder()
                .idDish(dishModel.getIdDish())
                .name(dishModel.getName())
                .descriptionDish(dishModel.getDescriptionDish())
                .price(dishModel.getPrice())
                .imageDish(dishModel.getImageDish())
                .stateDish(dishModel.getStateDish())
                .restaurantEntity(mapRestaurantModelToRestaurantEntity(dishModel.getRestaurantModel()))
                .categoryEntity(mapCategoryModelToCategoryEntity(dishModel.getCategoryModel()))
                .build();
    }

    private RestaurantEntity mapRestaurantModelToRestaurantEntity(RestaurantModel restaurantModelRequest) {
        if (restaurantModelRequest != null) {
            return RestaurantEntity.builder()
                    .idRestaurant(restaurantModelRequest.getIdRestaurant())
                    .name(restaurantModelRequest.getName())
                    .phone(restaurantModelRequest.getPhone())
                    .address(restaurantModelRequest.getAddress())
                    .urlLogo(restaurantModelRequest.getUrlLogo())
                    .nit(restaurantModelRequest.getNit())
                    .idOwner(restaurantModelRequest.getIdOwner())
                    .build();
        }
        return null;
    }
    private EmployeeRestaurantEntity mapEmployeeRestaurantModelToEmployeeRestaurantEntity(EmployeeRestaurantModel employeeRestaurantModelRequest) {
        if (employeeRestaurantModelRequest != null) {
            return EmployeeRestaurantEntity.builder()
                    .idRestaurantEmployee(employeeRestaurantModelRequest.getIdRestaurantEmployee())
                    .idEmployee(employeeRestaurantModelRequest.getIdUserEmployee())
                    .idRestaurant(employeeRestaurantModelRequest.getIdRestaurant())
                    .build();
        }
        return null;
    }
}
