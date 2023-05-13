package com.reto.plazoleta.application.handler.impl;

import com.reto.plazoleta.application.dto.request.*;
import com.reto.plazoleta.application.dto.response.UpdateDishResponseDto;
import com.reto.plazoleta.application.dto.response.UpdateDishStateResponseDto;
import com.reto.plazoleta.application.handler.IOwnerRestaurantService;
import com.reto.plazoleta.application.mapper.requestmapper.IDishRequestMapper;
import com.reto.plazoleta.application.mapper.responsemapper.IDishResponseMapper;
import com.reto.plazoleta.domain.api.IOwnerRestaurantServicePort;

import com.reto.plazoleta.domain.model.CategoryModel;
import com.reto.plazoleta.domain.model.DishModel;
import com.reto.plazoleta.domain.model.RestaurantModel;
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
    public void saveCategory(CreateCategoryRequestDto createCategoryRequestDto) {

    }

    @Override
    public void saveDish(CreateDishRequestDto createDishRequestDto) {
        DishModel dishModel = new DishModel();
        dishModel.setName(createDishRequestDto.getName());
        dishModel.setDescriptionDish(createDishRequestDto.getDescriptionDish());
        dishModel.setStateDish(createDishRequestDto.getStateDish());
        dishModel.setImageDish(createDishRequestDto.getImageDish());
        dishModel.setPrice(createDishRequestDto.getPrice());

        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setIdRestaurant(createDishRequestDto.getIdRestaurant());
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setIdCategory(createDishRequestDto.getIdCategory());

        dishModel.setCategoryModel(categoryModel);
        dishModel.setRestaurantModel(restaurantModel);
        ownerRestaurantServicePort.saveDish(dishModel);
    }

    @Override
    public UpdateDishResponseDto updateDish(UpdateDishRequestDto updateDishRequestDto) {
        return  dishResponseMapper.toDishUpdateResponse(ownerRestaurantServicePort
                .updateDish(dishRequestMapper
                        .toDishModel(updateDishRequestDto)));
    }
    @Override
    public UpdateDishStateResponseDto updateDishState(UpdateDishStateRequestDto updateDishStateRequestDto) {
        return dishResponseMapper.toDishUpdateStatuResponse(ownerRestaurantServicePort.updateDishState(dishRequestMapper
                .toDishModel(updateDishStateRequestDto)));
    }
}
