package com.reto.plazoleta.application.handler.impl;

import com.reto.plazoleta.application.dto.response.AssignedOrdersResponseDto;
import com.reto.plazoleta.application.dto.response.OrderDeliveredResponseDto;
import com.reto.plazoleta.application.dto.response.ResponseOrderDto;
import com.reto.plazoleta.application.dto.response.ResponseOrdersPaginatedDto;
import com.reto.plazoleta.application.handler.IEmployeeService;
import com.reto.plazoleta.application.mapper.responsemapper.ICustomerResponseMapper;
import com.reto.plazoleta.application.mapper.responsemapper.IEmployeeResponseMapper;
import com.reto.plazoleta.application.mapper.responsemapper.OrderMapper;
import com.reto.plazoleta.domain.api.IEmployeeRestaurantServicePort;
import com.reto.plazoleta.domain.model.OrderModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService {

    private final IEmployeeRestaurantServicePort employeeRestaurantServicePort;
    private final ICustomerResponseMapper customerResponseMapper;
    private final IEmployeeResponseMapper employeeResponseMapper;

    @Override
    public Page<ResponseOrdersPaginatedDto> getAllOrdersFilterByStatus(Integer sizeItems, Integer pageNumber, String status, String tokenWithPrefixBearer) {
        return this.employeeRestaurantServicePort
                .getAllOrdersFilterByStatusAndSizeItemsByPage(sizeItems, pageNumber, status, tokenWithPrefixBearer)
                .map(customerResponseMapper::orderModelToOrdersPaginatedResponseDto);
    }

    @Override
    public List<AssignedOrdersResponseDto> assignOrderAndChangeStatusToInPreparation(List<Long> idOrders, String tokenWithPrefixBearer) {
        return this.employeeRestaurantServicePort.assignEmployeeToOrderAndChangeStatusToInPreparation(idOrders, tokenWithPrefixBearer).stream()
                .map(employeeResponseMapper::orderModelToAssignedOrdersResponseDto).collect(Collectors.toList());
    }

    @Override
    public OrderDeliveredResponseDto changeOrderStatusToDelivered(Long orderPin, String tokenWithPrefixBearer) {
        return this.employeeResponseMapper.orderModelToOrderDeliveredResponseDto(
                this.employeeRestaurantServicePort.changeOrderStatusToDelivered(orderPin, tokenWithPrefixBearer)
        );
    }

    @Transactional
    @Override
    public ResponseOrderDto getOrderByPriority() {
        OrderModel pedidoConMayorPrioridad = this.employeeRestaurantServicePort.takeOrderByPriorityInStatusEarring();
        System.out.println(pedidoConMayorPrioridad.getIdOrder());
        return OrderMapper.orderModelToOrderTakenResponseDto(
                pedidoConMayorPrioridad
        );
    }

}
