package com.reto.plazoleta.application.handler.impl;

import com.reto.plazoleta.application.dto.request.CreateDishRequestDto;
import com.reto.plazoleta.application.dto.request.RequestEmployeeAccountDto;
import com.reto.plazoleta.application.dto.request.UpdateDishRequestDto;
import com.reto.plazoleta.application.dto.request.UpdateStateDishRequestDto;
import com.reto.plazoleta.application.dto.response.CreateDishResponseDto;
import com.reto.plazoleta.application.dto.response.ResponseEmployeeAccountDto;
import com.reto.plazoleta.application.dto.response.UpdateDishResponseDto;
import com.reto.plazoleta.application.mapper.responsemapper.IEmployeeResponseMapper;
import com.reto.plazoleta.application.mapper.requestmapper.IEmployeeRestaurantRequestMapper;
import com.reto.plazoleta.application.dto.response.UpdateStateDishResponseDto;
import com.reto.plazoleta.application.handler.IOwnerRestaurantService;
import com.reto.plazoleta.application.mapper.requestmapper.IDishRequestMapper;
import com.reto.plazoleta.application.mapper.responsemapper.IDishResponseMapper;
import com.reto.plazoleta.domain.api.IEmployeeRestaurantServicePort;
import com.reto.plazoleta.domain.api.IOwnerRestaurantServicePort;
import com.reto.plazoleta.domain.model.EmployeeRestaurantModel;
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
    private final IEmployeeRestaurantServicePort employeeRestaurantServicePort;
    private final IEmployeeRestaurantRequestMapper employeeRestaurantRequestMapper;
    private final IEmployeeResponseMapper employeeResponseMapper;


    @Override
    public CreateDishResponseDto saveDish(CreateDishRequestDto createDishRequestDto) {
        return dishResponseMapper.toDishResponse(ownerRestaurantServicePort.saveDish(dishRequestMapper.toDishModel(createDishRequestDto)));
    }

    @Override
    public UpdateDishResponseDto updateDish(UpdateDishRequestDto updateDishRequestDto) {
        return  dishResponseMapper.toDishUpdateResponse(ownerRestaurantServicePort
                .updateDish(dishRequestMapper
                        .toDishModel(updateDishRequestDto)));
    }

    @Override
    public UpdateStateDishResponseDto updateStateDish(UpdateStateDishRequestDto updateStateDishRequestDto) {
        return dishResponseMapper.toUpdateStateDishResponse(ownerRestaurantServicePort
                .updateStateDish(dishRequestMapper
                        .toStateDishModel(updateStateDishRequestDto)));
    }

    @Override
    public ResponseEmployeeAccountDto saveUserEmployeeInTheRestaurant(RequestEmployeeAccountDto requestEmployeeAccountDto, String tokenWithBearerPrefix) {
        final EmployeeRestaurantModel employeeRestaurantRequestModel = this.employeeRestaurantRequestMapper.toEmployeeRestaurantModel(requestEmployeeAccountDto);
        final EmployeeRestaurantModel employeeRestaurantSavedModel = this.employeeRestaurantServicePort.saveEmployeeRestaurant(
                employeeRestaurantRequestModel, tokenWithBearerPrefix);
        return this.employeeResponseMapper.toRestaurantEmployeeResponseDto(employeeRestaurantSavedModel);
    }
}
