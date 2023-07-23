package com.reto.plazoleta.application.handler;

import com.reto.plazoleta.application.dto.request.CreateOrderRequestDto;
import com.reto.plazoleta.application.dto.request.OrderDishTypeRequestDto;
import com.reto.plazoleta.application.dto.request.OrderWithASingleDishDto;
import com.reto.plazoleta.application.dto.response.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICustomerService {

    Page<RestaurantResponsePaginatedDto> getAllRestaurantsByOrderByNameAsc(int numberPage, int sizeItems);

    CreateOrderResponseDto saveOrder(CreateOrderRequestDto createOrderRequestDto, String tokenWithPrefixBearer);

    CanceledOrderResponseDto cancelOrder(Long idOrder, String tokenWithPrefixBearer);

    Page<CategoryFromDishesPaginatedResponseDto> getDishesFromARestaurantAndGroupedByCategoryPaginated(Integer numberPage, Integer sizeItems, Long idRestaurant);

    SingleDishOrderResponseDto addSingleDishOrder(OrderWithASingleDishDto orderWithASingleDishDto, Long idRestaurant);

    List<OrderDishTypeDtoResponse> addOrderWithMultipleDishes(List<OrderDishTypeRequestDto> ordersDishesTypeRequest, Long idRestaurantFromOrder);

}