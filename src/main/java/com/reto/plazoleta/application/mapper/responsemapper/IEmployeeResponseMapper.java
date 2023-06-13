package com.reto.plazoleta.application.mapper.responsemapper;

import com.reto.plazoleta.application.dto.response.ResponseEmployeeAccountDto;
import com.reto.plazoleta.domain.model.EmployeeRestaurantModel;
import org.mapstruct.Mapper;

@Mapper
public interface IEmployeeResponseMapper {

    ResponseEmployeeAccountDto toRestaurantEmployeeResponseDto(EmployeeRestaurantModel employeeRestaurantModel);
}
