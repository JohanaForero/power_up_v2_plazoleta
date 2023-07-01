package com.reto.plazoleta.application.handler.impl;

import com.reto.plazoleta.application.dto.response.OrderDto;
import com.reto.plazoleta.application.handler.IOrderService;
import com.reto.plazoleta.application.mapper.responsemapper.OrderMapper;
import com.reto.plazoleta.domain.api.IEmployeeRestaurantServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final IEmployeeRestaurantServicePort employeeServicePort;
    private OrderMapper orderMapper;

    @Override
    public OrderDto getTakeOrderPriority() {
        return this.orderMapper.orderModelToOrderDto(
                this.employeeServicePort.getTakeOrdersPriority());
    }
}
