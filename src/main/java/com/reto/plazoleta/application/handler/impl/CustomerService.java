package com.reto.plazoleta.application.handler.impl;

import com.reto.plazoleta.application.dto.response.RestaurantResponsePageableDto;
import com.reto.plazoleta.application.handler.ICustomerService;
import com.reto.plazoleta.application.mapper.pageresponse.IRestaurantPageResponseMapper;
import com.reto.plazoleta.domain.api.IRestaurantServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomerService implements ICustomerService {

    private final IRestaurantServicePort restaurantServicePort;
    private final IRestaurantPageResponseMapper restaurantPageResponseMapper;

    @Override
    public Page<RestaurantResponsePageableDto> getAllRestaurantsByOrderByNameAsc(int numberPage, int sizeItems) {
        return restaurantPageResponseMapper.toRestaurantResponsePageable(
                restaurantServicePort.findAllByOrderByNameAsc(numberPage, sizeItems));
    }
}
