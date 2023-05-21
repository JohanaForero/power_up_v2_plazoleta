package com.reto.plazoleta.application.handler;

import com.reto.plazoleta.application.dto.request.CreateDishRequestDto;
import com.reto.plazoleta.application.dto.response.CreateDishResponseDto;

public interface IOwnerRestaurantService {

    CreateDishResponseDto saveDish(CreateDishRequestDto createDishRequestDto);
}
