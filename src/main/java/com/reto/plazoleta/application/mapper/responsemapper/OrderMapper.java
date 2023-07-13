package com.reto.plazoleta.application.mapper.responsemapper;

import com.reto.plazoleta.application.dto.response.ResponseOrderDto;
import com.reto.plazoleta.application.dto.response.ResponseTypeDishOrderDto;
import com.reto.plazoleta.domain.model.OrderDishModel;
import com.reto.plazoleta.domain.model.OrderModel;
import com.reto.plazoleta.domain.model.dishes.DishModel;
import com.reto.plazoleta.domain.model.dishes.Meat;
import com.reto.plazoleta.domain.model.dishes.Soup;
import com.reto.plazoleta.domain.model.dishes.deseerts.FlanModel;
import com.reto.plazoleta.domain.model.dishes.deseerts.IceCreamModel;
import lombok.experimental.UtilityClass;

import java.util.stream.Collectors;

@UtilityClass
public class OrderMapper {

    private static final String TYPE_DISH_MEAT = "Carne";
    private static final String TYPE_DISH_SOUP = "Sopas";
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
            System.out.println("carne");
            return buildMeatDishTypeOrderedResponse((Meat) dish);
        } else if (dish instanceof Soup) {
            System.out.println("Soup");
            return buildSoupDishTypeOrderedResponse((Soup) dish);
        } else if (dish instanceof FlanModel) {
            System.out.println("FlanModel");
            return buildFlanDessertTypeOrderedResponse((FlanModel) dish);
        } else if (dish instanceof IceCreamModel) {
            System.out.println("IceCreamModel");
            return buildIceCreamDessertTypeOrderedResponse((IceCreamModel) dish);
        }
        System.out.println("nada");
        return null;
    }

    private ResponseTypeDishOrderDto buildMeatDishTypeOrderedResponse(Meat meatDish) {
        ResponseTypeDishOrderDto dishTypeOrderedResponse = new ResponseTypeDishOrderDto();
        dishTypeOrderedResponse.setDessertType(TYPE_DISH_MEAT);
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
        dishTypeOrderedResponse.setAccompaniment(flanDessertDish.getAccompaniment());
        return dishTypeOrderedResponse;
    }

    private ResponseTypeDishOrderDto buildIceCreamDessertTypeOrderedResponse(IceCreamModel iceCreamDessertDish) {
        ResponseTypeDishOrderDto dishTypeOrderedResponse = new ResponseTypeDishOrderDto();
        dishTypeOrderedResponse.setTypeDish(TYPE_DISH_ICE_CREAM_DESSERT);
        dishTypeOrderedResponse.setFlavor(iceCreamDessertDish.getFlavor());
        return dishTypeOrderedResponse;
    }
}

