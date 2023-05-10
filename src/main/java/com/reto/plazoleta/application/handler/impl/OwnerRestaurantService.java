package com.reto.plazoleta.application.handler.impl;

import com.reto.plazoleta.application.dto.request.CreateDishRequestDto;
import com.reto.plazoleta.application.dto.request.UpdateDishRequestDto;
import com.reto.plazoleta.application.dto.request.UpdateDishStateRequestDto;
import com.reto.plazoleta.application.dto.response.UpdateDishResponseDto;
import com.reto.plazoleta.application.dto.response.UpdateDishStateResponseDto;
import com.reto.plazoleta.application.handler.IOwnerRestaurantService;
import com.reto.plazoleta.application.mapper.requestmapper.IDishRequestMapper;
import com.reto.plazoleta.application.mapper.responsemapper.IDishResponseMapper;
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
    private final IDishResponseMapper dishResponseMapper;

    @Override
    public void saveDish(CreateDishRequestDto createDishRequestDto) {
        ownerRestaurantServicePort.saveDish(dishRequestMapper.toDishModel(createDishRequestDto));
    }
    public UpdateDishResponseDto updateDish(UpdateDishRequestDto updateDishRequestDto) {
        return  dishResponseMapper.toDishUpdateResponse(ownerRestaurantServicePort
                .updateDish(dishRequestMapper
                        .toDishModel(updateDishRequestDto)));
    }
    public UpdateDishStateResponseDto updateDishState(UpdateDishStateRequestDto updateDishStateRequestDto) {
        return dishResponseMapper.toDishUpdateStatuResponse(ownerRestaurantServicePort.updateDishState(dishRequestMapper
                .toDishModel(updateDishStateRequestDto)));
    }
}
