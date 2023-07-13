package com.reto.plazoleta.domain.model;

import com.reto.plazoleta.domain.model.dishes.DishModel;
import com.reto.plazoleta.domain.model.dishes.Meat;
import com.reto.plazoleta.domain.model.dishes.Soup;
import com.reto.plazoleta.domain.model.dishes.deseerts.Desserts;
import com.reto.plazoleta.domain.model.dishes.deseerts.FlanModel;
import com.reto.plazoleta.domain.model.dishes.deseerts.IceCreamModel;

import java.util.Comparator;
import java.util.List;

public class OrderDishComparator implements Comparator<OrderModel> {

    private static final String YUCCA_GARNISH_SOUP_DISH = "Yuca";
    private static final String POTATO_GARNISH_SOUP_DISH = "Papa";
    private static final String RICE_GARNISH_SOUP_DISH = "Arroz";


    @Override
    public int compare(OrderModel order, OrderModel nextOrder) {
        int orderPriority = calculateOrderPriority(order.getOrdersDishesModel());
        int nextOrderPriority = calculateOrderPriority(nextOrder.getOrdersDishesModel());
        return Integer.compare(nextOrderPriority, orderPriority);
    }

    private int calculateOrderPriority(List<OrderDishModel> orderDishes) {
        int totalPriorityForDishes = 0;
        for (OrderDishModel orderDish : orderDishes) {
            totalPriorityForDishes += calculateDishPriority(orderDish.getDishModel());
        }
        return totalPriorityForDishes;
    }

    private int calculateDishPriority(DishModel dish) {
        if (dish instanceof Meat) {
            return calculateMeatPriority((Meat) dish);
        } else if (dish instanceof Soup) {
            return calculateSoupPriority((Soup) dish);
        } else if (dish instanceof Desserts) {
            return calculateDessertPriority((Desserts) dish);
        }
        return 0;
    }

    private int calculateMeatPriority(Meat meat) {
        return meat.getGrams();
    }

    private int calculateSoupPriority(Soup soup) {
        String accompaniment = soup.getAccompaniment();
        if (accompaniment.equalsIgnoreCase(YUCCA_GARNISH_SOUP_DISH)) {
            return 3;
        } else if (accompaniment.equalsIgnoreCase(POTATO_GARNISH_SOUP_DISH)) {
            return 2;
        } else if (accompaniment.equalsIgnoreCase(RICE_GARNISH_SOUP_DISH)) {
            return 1;
        }
        return 0;
    }

    private int calculateDessertPriority(Desserts dessert) {
        if (dessert instanceof FlanModel) {
            return 2;
        } else if (dessert instanceof IceCreamModel) {
            return 1;
        }
        return 0;
    }

}
