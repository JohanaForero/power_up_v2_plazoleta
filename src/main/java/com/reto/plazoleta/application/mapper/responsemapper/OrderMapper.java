package com.reto.plazoleta.application.mapper.responsemapper;

import com.reto.plazoleta.application.dto.response.OrderDto;
import com.reto.plazoleta.domain.model.OrderModel;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OrderMapper {

    public static OrderDto orderModelToOrderDto(OrderModel orderModel) {
        return OrderDto.builder()
                .id(orderModel.getIdOrder())
                .idUserCustomer(orderModel.getIdUserCustomer())
                .date(orderModel.getDate())
                .employee(orderModel.getEmployeeRestaurantModel())
                .status(orderModel.getStatus().toString())
                .build();
    }
}
