package com.reto.plazoleta.application.mapper.requestmapper;

import com.reto.plazoleta.application.dto.request.CreateDishRequestDto;
import com.reto.plazoleta.application.dto.request.UpdateDishRequestDto;
import com.reto.plazoleta.application.dto.request.UpdateStateDishRequestDto;
import com.reto.plazoleta.domain.model.dishes.DishModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface IDishRequestMapper {

    @Mapping(target = "restaurantModel.idRestaurant", source = "createDishRequestDto.idRestaurant")
    @Mapping(target = "categoryModel.idCategory", source = "createDishRequestDto.idCategory")
    @Mapping(target = "stateDish", ignore = true)
    @Mapping(target = "idDish", ignore = true)
    DishModel toDishModel(CreateDishRequestDto createDishRequestDto);

    @Mapping(target = "restaurantModel.idRestaurant", source = "updateDishRequestDto.idRestaurant")
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "imageDish", ignore = true)
    @Mapping(target = "stateDish", ignore = true)
    @Mapping(target = "categoryModel", ignore = true)
    @Mapping(target = "price", source = "updateDishRequestDto.price")
    @Mapping(target = "descriptionDish", source = "updateDishRequestDto.descriptionDish")
    DishModel toDishModel(UpdateDishRequestDto updateDishRequestDto);

    @Mapping(target = "name", ignore = true)
    @Mapping(target = "imageDish", ignore = true)
    @Mapping(target = "categoryModel", ignore = true)
    @Mapping(target = "price", ignore = true)
    @Mapping(target = "descriptionDish",ignore = true)
    @Mapping(target = "restaurantModel.idRestaurant", source = "updateStateDishRequestDto.idRestaurant")
    @Mapping(target = "stateDish", source = "updateStateDishRequestDto.stateDish")
    DishModel toStateDishModel(UpdateStateDishRequestDto updateStateDishRequestDto);
}
