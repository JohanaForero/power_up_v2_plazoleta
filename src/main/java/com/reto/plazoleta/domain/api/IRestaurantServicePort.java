package com.reto.plazoleta.domain.api;

import com.reto.plazoleta.domain.model.RestaurantModel;
import org.springframework.data.domain.Page;

public interface IRestaurantServicePort {
    void saveRestaurant(RestaurantModel restaurantModel);
    Page<RestaurantModel> findAllByOrderByName(int numberPage, int sizeItems);
}
