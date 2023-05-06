package com.reto.plazoleta.application.handler.impl;

import com.reto.plazoleta.application.dto.request.RequestToCreateRestaurantDto;
import com.reto.plazoleta.application.handler.IAdminService;
import com.reto.plazoleta.application.mapper.requestmapper.IRestaurantRequestMapper;
import com.reto.plazoleta.domain.api.IRestaurantServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class AdminService implements IAdminService {

    private final IRestaurantServicePort restaurantServicePort;
    private final IRestaurantRequestMapper restaurantRequestMapper;

    @Override
    public void saveRestaurant(RequestToCreateRestaurantDto requestToCreateRestaurantDto, String tokenWithBearerPrefix) {
        restaurantServicePort.saveRestaurant(
                restaurantRequestMapper.toRestaurantModel(requestToCreateRestaurantDto), tokenWithBearerPrefix );
    }
}
