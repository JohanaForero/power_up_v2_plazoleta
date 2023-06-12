package com.reto.plazoleta.application.handler;

import com.reto.plazoleta.application.dto.request.RequestToCreateRestaurantDto;
import com.reto.plazoleta.application.dto.response.ResponseToCreateRestaurantDto;

public interface IAdminService {

    ResponseToCreateRestaurantDto saveRestaurant(RequestToCreateRestaurantDto requestToCreateRestaurantDto, String tokenWithBearerPrefix);
}
