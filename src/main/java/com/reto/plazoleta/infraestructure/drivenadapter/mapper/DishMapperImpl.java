package com.reto.plazoleta.infraestructure.drivenadapter.mapper;

import com.reto.plazoleta.domain.model.*;
import com.reto.plazoleta.domain.model.dishes.DishModel;
import com.reto.plazoleta.domain.model.dishes.Meat;
import com.reto.plazoleta.domain.model.dishes.Soup;
import com.reto.plazoleta.domain.model.dishes.deseerts.FlanModel;
import com.reto.plazoleta.domain.model.dishes.deseerts.IceCreamModel;
import com.reto.plazoleta.infraestructure.drivenadapter.entity.*;
import lombok.experimental.UtilityClass;
import org.hibernate.Hibernate;

import java.util.Collections;
import java.util.stream.Collectors;

@UtilityClass
public class DishMapperImpl {

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
                        .map(DishMapperImpl::convertOrderDishEntityToOrderDishModel)
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
        DishEntity dishEntity = (DishEntity) Hibernate.unproxy(orderDishEntity.getDishEntity());
        return new OrderDishModel(
                orderDishEntity.getIdOrderDish(),
                mapOrderEntityToOrderModelWithoutListOrdersDishes(orderDishEntity.getOrderEntity()),
                convertDishEntityToDishModel( dishEntity, orderDishEntity),
                orderDishEntity.getAmount(),
                orderDishEntity.getGrams(),
                orderDishEntity.getAccompaniment(),
                orderDishEntity.getFlavor()
        );
    }

    private DishModel convertDishEntityToDishModel(DishEntity dishEntity, OrderDishEntity orderDishEntity) {
        CategoryEntity categoryEntity = dishEntity.getCategoryEntity();
        String dishType = categoryEntity.getName().toString();
        if (dishType.equalsIgnoreCase(TypeDish.CARNE.toString())) {
            return convertToMeatDishModel(dishEntity, orderDishEntity.getGrams());
        } else if (dishType.equalsIgnoreCase(TypeDish.SOPAS.toString())) {
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

