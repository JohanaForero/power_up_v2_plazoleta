package com.reto.plazoleta.application.handler.impl;

import com.reto.plazoleta.application.dto.request.CreateOrderRequestDto;
import com.reto.plazoleta.application.dto.request.OrderWithASingleDishDto;
import com.reto.plazoleta.application.dto.response.*;
import com.reto.plazoleta.application.handler.ICustomerService;
import com.reto.plazoleta.application.mapper.requestmapper.ICustomerRequestMapper;
import com.reto.plazoleta.application.mapper.requestmapper.IRestaurantRequestMapper;
import com.reto.plazoleta.application.mapper.responsemapper.ICustomerResponseMapper;
import com.reto.plazoleta.application.mapper.responsemapper.OrderMapper;
import com.reto.plazoleta.domain.api.ICustomerServicePort;
import com.reto.plazoleta.domain.api.IRestaurantServicePort;
import com.reto.plazoleta.domain.model.OrderDishModel;
import com.reto.plazoleta.domain.model.OrderModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CustomerService implements ICustomerService {

    private final IRestaurantServicePort restaurantServicePort;
    private final IRestaurantRequestMapper restaurantRequestMapper;
    private final ICustomerServicePort customerServicePort;
    private final ICustomerRequestMapper customerRequestMapper;
    private final ICustomerResponseMapper customerResponseMapper;
    private OrderMapper orderMapper;

    @Override
    public Page<RestaurantResponsePaginatedDto> getAllRestaurantsByOrderByNameAsc(int numberPage, int sizeItems) {
        return restaurantServicePort.findAllByOrderByNameAsc(numberPage, sizeItems).map(restaurantRequestMapper::toRestaurantResponse);
    }

    @Override
    public Page<CategoryFromDishesPaginatedResponseDto> getDishesFromARestaurantAndGroupedByCategoryPaginated(Integer numberPage, Integer sizeItems, Long idRestaurant) {
        return this.customerServicePort.getAllDishesActivePaginatedFromARestaurantOrderByCategoryAscending(numberPage, sizeItems, idRestaurant)
                .stream()
                .collect(Collectors.groupingBy(
                        dishModelResponse -> dishModelResponse.getCategoryModel().getIdCategory()
                ))
                .entrySet()
                .stream()
                .map(this.customerResponseMapper::mapEntryToCategoryFromDishesPaginatedResponseDto)
                .collect(Collectors.collectingAndThen(Collectors.toList(), content -> new PageImpl<>(content, PageRequest.of(numberPage, sizeItems), content.size())));
    }

    @Override
    public CreateOrderResponseDto saveOrder(CreateOrderRequestDto createOrderRequestDto, String tokenWithPrefixBearer) {
        OrderModel orderRequestModel = this.customerRequestMapper.toOrderModel(createOrderRequestDto);
        final List<OrderDishModel> ordersDishesRequest = createOrderRequestDto.getDishes().stream().map(this.customerRequestMapper::toOrderDishModel).collect(Collectors.toList());
        orderRequestModel.setOrdersDishesModel(ordersDishesRequest);
        return this.customerResponseMapper.toCreateOrderResponseDto(customerServicePort.saveOrder(orderRequestModel, tokenWithPrefixBearer));
    }

    @Override
    public CanceledOrderResponseDto cancelOrder(Long idOrder, String tokenWithPrefixBearer) {
        final OrderModel orderCanceledModel = this.customerServicePort.orderCanceled(idOrder, tokenWithPrefixBearer);
        return this.customerResponseMapper.orderModelToOrderCanceledResponseDto(orderCanceledModel);
    }

    @Transactional
    @Override
    public SingleDishOrderResponseDto addSingleDishOrder(OrderWithASingleDishDto orderWithASingleDishDto, Long idRestaurant) {
        final OrderModel orderModelRequest = orderMapper.singleDishOrderRequestDtoToOrderModel(orderWithASingleDishDto, idRestaurant);
        final OrderModel orderModelResponse = this.customerServicePort.addSingleDishOrder(orderModelRequest);
        return orderMapper.orderModelToSingleDishOrderResponseDto(orderModelResponse);
    }
}
