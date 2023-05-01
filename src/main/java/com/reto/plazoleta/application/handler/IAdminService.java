package com.reto.plazoleta.application.handler;

import com.reto.plazoleta.application.dto.request.RequestToCreateRestaurantDto;

public interface IAdminService {

    void saveRestaurant(RequestToCreateRestaurantDto requestToCreateRestaurantDto);
}
