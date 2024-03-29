package com.reto.plazoleta.application.mapper.requestmapper;

import com.reto.plazoleta.application.dto.request.RequestEmployeeAccountDto;
import com.reto.plazoleta.domain.model.EmployeeRestaurantModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface IEmployeeRestaurantRequestMapper {

    @Mapping(target = "idRestaurantEmployee", ignore = true)
    EmployeeRestaurantModel toEmployeeRestaurantModel(RequestEmployeeAccountDto requestEmployeeAccountDto);
}