package com.reto.plazoleta.application.handler;

import com.reto.plazoleta.application.dto.request.CreateOrderRequestDto;
import com.reto.plazoleta.application.dto.response.CanceledOrderResponseDto;
import com.reto.plazoleta.application.dto.response.CreateOrderResponseDto;
import com.reto.plazoleta.application.dto.response.RestaurantResponsePaginatedDto;
import org.springframework.data.domain.Page;

public interface ICustomerService {

    Page<RestaurantResponsePaginatedDto> getAllRestaurantsByOrderByNameAsc(int numberPage, int sizeItems);

    CreateOrderResponseDto saveOrder(CreateOrderRequestDto createOrderRequestDto, String tokenWithPrefixBearer);

    CanceledOrderResponseDto cancelOrder(Long idOrder, String tokenWithPrefixBearer);
}