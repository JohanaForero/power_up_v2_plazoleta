package com.reto.plazoleta.application.mapper.responsemapper;

import com.reto.plazoleta.application.dto.response.CreateDishResponseDto;
import com.reto.plazoleta.application.dto.response.DishOfACategoryResponseDto;
import com.reto.plazoleta.application.dto.response.ResponseMenuDto;
import com.reto.plazoleta.application.dto.response.UpdateDishResponseDto;
import com.reto.plazoleta.application.dto.response.UpdateStateDishResponseDto;
import com.reto.plazoleta.domain.model.DishModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;

@Mapper
public interface IDishResponseMapper {

    CreateDishResponseDto toDishResponse(DishModel dishModel);

    UpdateDishResponseDto toDishUpdateResponse(DishModel dishModel);

    UpdateStateDishResponseDto toUpdateStateDishResponse(DishModel dishModel);

    @Mapping(target = "idCategory", source = "categoryModel.idCategory")
    @Mapping(target = "name", source = "categoryModel.name")
    @Mapping(target = "dishes", expression = "java(mapToResponseMenuDtoList(dishModel))")
    DishOfACategoryResponseDto toResponseMenuDto(DishModel dishModel);

    ResponseMenuDto mapToResponseMenuDto(DishModel dishModel);

    default List<ResponseMenuDto> mapToResponseMenuDtoList(DishModel dishModel) {
        return Collections.singletonList(mapToResponseMenuDto(dishModel));
    }
}
