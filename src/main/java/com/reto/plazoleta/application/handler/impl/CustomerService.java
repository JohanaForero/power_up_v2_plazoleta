package com.reto.plazoleta.application.handler.impl;

import com.reto.plazoleta.application.dto.response.DishOfACategoryResponseDto;
import com.reto.plazoleta.application.dto.response.RestaurantResponsePageableDto;
import com.reto.plazoleta.application.handler.ICustomerService;
import com.reto.plazoleta.application.mapper.requestmapper.IRestaurantRequestMapper;
import com.reto.plazoleta.application.mapper.responsemapper.IDishResponseMapper;
import com.reto.plazoleta.domain.api.ICustomerServicePort;
import com.reto.plazoleta.domain.api.IRestaurantServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomerService implements ICustomerService {

    private final IRestaurantServicePort restaurantServicePort;
    private final IRestaurantRequestMapper restaurantRequestMapper;
    private final ICustomerServicePort customerServicePort;
    private final IDishResponseMapper dishResponseMapper;

    @Override
    public Page<RestaurantResponsePageableDto> getAllRestaurantsByOrderByNameAsc(int numberPage, int sizeItems) {
        return restaurantServicePort.findAllByOrderByNameAsc(numberPage, sizeItems).map(restaurantRequestMapper::toRestaurantResponse);
    }

    @Override
    public Page<DishOfACategoryResponseDto> getAllDishByOrderByCategory(Integer numberPage, Integer sizeItems, Long idRestaurant) {
        return customerServicePort.findAllDishByIdRestaurantAndGroupByCategoryPaginated(numberPage, sizeItems, idRestaurant)
                                .map(dishResponseMapper::toResponseMenuDto);
    }
}
