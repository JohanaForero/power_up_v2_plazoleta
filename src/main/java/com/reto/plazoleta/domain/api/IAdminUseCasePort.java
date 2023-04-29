package com.reto.plazoleta.domain.api;

import com.reto.plazoleta.domain.model.RestaurantModel;
import org.springframework.data.domain.Page;

public interface IAdminUseCasePort {

    void saveRestaurant(RestaurantModel restaurantModel);
}
