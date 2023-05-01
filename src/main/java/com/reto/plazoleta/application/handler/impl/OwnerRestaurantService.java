package com.reto.plazoleta.application.handler.impl;

import com.reto.plazoleta.application.dto.request.CreateDishRequestDto;
import com.reto.plazoleta.application.dto.request.UpdateDishRequestDto;
import com.reto.plazoleta.application.handler.IOwnerRestaurantService;
import com.reto.plazoleta.application.mapper.requestmapper.IDishRequestMapper;
import com.reto.plazoleta.domain.api.IOwnerRestaurantServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class OwnerRestaurantService implements IOwnerRestaurantService {

    private final IOwnerRestaurantServicePort ownerRestaurantServicePort;
    private final IDishRequestMapper dishRequestMapper;

    @Override
    public void saveDish(CreateDishRequestDto createDishRequestDto) {
        ownerRestaurantServicePort.saveDish(dishRequestMapper.toDishModel(createDishRequestDto));
    }
    public void updateDish(UpdateDishRequestDto updateDishRequestDto) {
        ownerRestaurantServicePort.updateDish(dishRequestMapper.toDishModel(updateDishRequestDto));
    }
}
