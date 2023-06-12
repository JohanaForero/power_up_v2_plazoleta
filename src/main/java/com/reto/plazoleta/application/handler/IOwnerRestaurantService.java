package com.reto.plazoleta.application.handler;

import com.reto.plazoleta.application.dto.request.CreateDishRequestDto;
import com.reto.plazoleta.application.dto.request.RequestEmployeeAccountDto;
import com.reto.plazoleta.application.dto.request.UpdateDishRequestDto;
import com.reto.plazoleta.application.dto.request.UpdateStateDishRequestDto;
import com.reto.plazoleta.application.dto.response.CreateDishResponseDto;
import com.reto.plazoleta.application.dto.response.ResponseEmployeeAccountDto;
import com.reto.plazoleta.application.dto.response.UpdateDishResponseDto;
import com.reto.plazoleta.application.dto.response.UpdateStateDishResponseDto;

public interface IOwnerRestaurantService {

    CreateDishResponseDto saveDish(CreateDishRequestDto createDishRequestDto);

    UpdateDishResponseDto updateDish(UpdateDishRequestDto updateDishRequestDto);

    UpdateStateDishResponseDto updateStateDish(UpdateStateDishRequestDto updateStateDishRequestDto);

    ResponseEmployeeAccountDto saveUserEmployeeInTheRestaurant(RequestEmployeeAccountDto requestEmployeeAccountDto, String tokenWithBearerPrefix);
}