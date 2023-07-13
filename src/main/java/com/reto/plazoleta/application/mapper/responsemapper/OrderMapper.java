package com.reto.plazoleta.application.mapper.responsemapper;

import com.reto.plazoleta.application.dto.response.ResponseOrderDto;
import com.reto.plazoleta.application.dto.response.ResponseTypeDishOrderDto;
import com.reto.plazoleta.domain.model.dishs.*;
import com.reto.plazoleta.domain.model.OrderDishModel;
import com.reto.plazoleta.domain.model.OrderModel;
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
}
