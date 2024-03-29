package com.reto.plazoleta.domain.model;

import com.reto.plazoleta.domain.model.dishs.*;

import java.util.Comparator;
import java.util.List;

public class OrderPriorityOrganizer implements Comparator<OrderModel> {

    private static final String RICE_GARNISH_SOUP_DISH = "Arroz";
    private static final String YUCCA_GARNISH_SOUP_DISH = "Yuca";
    private static final String POTATO_GARNISH_SOUP_DISH = "Papa";
    private static final int YUCCA_PRIORITY = 10;
    private static final int POTATO_PRIORITY = 8;
    private static final int RICE_PRIORITY = 6;
    private static final int FLAN_DESSERT_PRIORITY = 4;
    private static final int ICE_CREAM_DESSERT_PRIORITY = 2;
    @Override
    public int compare(OrderModel order, OrderModel nextOrder) {
        int orderPriority = calculateOrderDishesPriorityTotal(order.getOrdersDishesModel());
        int nextOrderPriority = calculateOrderDishesPriorityTotal(nextOrder.getOrdersDishesModel());
        return Integer.compare(nextOrderPriority, orderPriority);
    }
    private int calculateOrderDishesPriorityTotal(List<OrderDishModel> orderDishes) {
        int totalPriorityForDishes = 0;
        for (OrderDishModel orderDish : orderDishes) {
            totalPriorityForDishes += calculatePriorityOfDish(orderDish.getDishModel());
        }
        return totalPriorityForDishes;
    }

    private int calculatePriorityOfDish(DishModel dish) {
        if (dish instanceof Meat) {
            return ((Meat) dish).getGrams();
        } else if (dish instanceof Soup) {
            return calculatePriorityOfSoupDish((Soup) dish);
        } else if (dish instanceof Desserts) {
            return calculatePriorityOfDessertDish((Desserts) dish);
        }
        return 0;
    }

    private int calculatePriorityOfSoupDish(Soup soupDish) {
        String sideDish = soupDish.getAccompaniment();
        switch (sideDish) {
            case YUCCA_GARNISH_SOUP_DISH:
                return YUCCA_PRIORITY;
            case POTATO_GARNISH_SOUP_DISH:
                return POTATO_PRIORITY;
            case RICE_GARNISH_SOUP_DISH:
                return RICE_PRIORITY;
            default:
                return 0;
        }
    }

    private int calculatePriorityOfDessertDish(Desserts dessertDish) {
        if (dessertDish instanceof FlanModel) {
            return FLAN_DESSERT_PRIORITY;
        } else if (dessertDish instanceof IceCreamModel) {
            return ICE_CREAM_DESSERT_PRIORITY;
        }
        return 0;
    }
}
