package com.reto.plazoleta.application.handler;

import com.reto.plazoleta.application.dto.request.CreateDishRequestDto;
import com.reto.plazoleta.application.dto.request.UpdateDishRequestDto;
import com.reto.plazoleta.application.dto.response.CreateDishResponseDto;
import com.reto.plazoleta.application.dto.response.UpdateDishResponseDto;

public interface IOwnerRestaurantService {

    CreateDishResponseDto saveDish(CreateDishRequestDto createDishRequestDto);

    UpdateDishResponseDto updateDish(UpdateDishRequestDto updateDishRequestDto);
}
