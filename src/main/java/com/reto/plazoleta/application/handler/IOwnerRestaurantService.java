package com.reto.plazoleta.application.handler;

import com.reto.plazoleta.application.dto.request.CreateDishRequestDto;
import com.reto.plazoleta.application.dto.request.UpdateDishRequestDto;

public interface IOwnerRestaurantService {

    void saveDish(CreateDishRequestDto createDishRequestDto);

    void updateDish(UpdateDishRequestDto updateDishRequestDto);
}
