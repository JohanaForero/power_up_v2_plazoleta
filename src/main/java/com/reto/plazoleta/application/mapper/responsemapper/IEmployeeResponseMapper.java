package com.reto.plazoleta.application.mapper.responsemapper;

import com.reto.plazoleta.application.dto.response.AssignedOrdersResponseDto;
import com.reto.plazoleta.application.dto.response.ResponseEmployeeAccountDto;
import com.reto.plazoleta.domain.model.EmployeeRestaurantModel;
import com.reto.plazoleta.domain.model.OrderModel;
import org.mapstruct.Mapper;

@Mapper
public interface IEmployeeResponseMapper {

    AssignedOrdersResponseDto orderModelToAssignedOrdersResponseDto(OrderModel orderModel);
    ResponseEmployeeAccountDto toRestaurantEmployeeResponseDto(EmployeeRestaurantModel employeeRestaurantModel);
}