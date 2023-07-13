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
}
