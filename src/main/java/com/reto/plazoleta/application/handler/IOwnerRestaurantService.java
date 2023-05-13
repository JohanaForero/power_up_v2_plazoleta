package com.reto.plazoleta.application.handler;

import com.reto.plazoleta.application.dto.request.CreateDishRequestDto;

public interface IOwnerRestaurantService {

    void saveDish(CreateDishRequestDto createDishRequestDto);

}
