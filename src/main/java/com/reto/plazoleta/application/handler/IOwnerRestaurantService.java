package com.reto.plazoleta.application.handler;

import com.reto.plazoleta.application.dto.request.CreateDishRequestDto;
import com.reto.plazoleta.application.dto.request.UpdateDishRequestDto;
import com.reto.plazoleta.application.dto.request.UpdateDishStateRequestDto;
import com.reto.plazoleta.application.dto.response.UpdateDishResponseDto;
import com.reto.plazoleta.application.dto.response.UpdateDishStateResponseDto;

public interface IOwnerRestaurantService {

    void saveDish(CreateDishRequestDto createDishRequestDto);

    UpdateDishResponseDto updateDish(UpdateDishRequestDto updateDishRequestDto);

    UpdateDishStateResponseDto updateDishState(UpdateDishStateRequestDto updateDishStateRequestDto);


}
