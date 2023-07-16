package com.reto.plazoleta.domain.model.dishs;

import com.reto.plazoleta.domain.model.OrderDishModel;

import java.util.Comparator;

public class ComparatorDishModel implements Comparator<OrderDishModel> {

    private static final String RICE_GARNISH_SOUP_DISH = "ARROZ";
    private static final String YUCCA_GARNISH_SOUP_DISH = "YUCA";
    private static final String POTATO_GARNISH_SOUP_DISH = "PAPA";
    private static final int YUCCA = 30;
    private static final int POTATO = 25;
    private static final int RICE = 20;
    private static final int FLAN = 15;
    private static final int ICE_CREAM = 10;

    @Override
    public int compare(OrderDishModel dish1, OrderDishModel dish2) {
        int dishPriority = calculatePriorityOfDish(dish1.getDishModel());
        int nextDishPriority = calculatePriorityOfDish(dish2.getDishModel());
        return Integer.compare(dishPriority, nextDishPriority);
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
        String sideDish = soupDish.getAccompaniment().toUpperCase();
        switch (sideDish) {
            case YUCCA_GARNISH_SOUP_DISH:
                return YUCCA;
            case POTATO_GARNISH_SOUP_DISH:
                return POTATO;
            case RICE_GARNISH_SOUP_DISH:
                return RICE;
            default:
                return 0;
        }
    }

    private int calculatePriorityOfDessertDish(Desserts dessertDish) {
        if (dessertDish instanceof FlanModel) {
            return FLAN;
        } else if (dessertDish instanceof IceCreamModel) {
            return ICE_CREAM;
        }
        return 0;
    }
}
