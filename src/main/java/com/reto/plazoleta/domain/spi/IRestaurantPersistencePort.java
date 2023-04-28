package com.reto.plazoleta.domain.spi;

import com.reto.plazoleta.domain.model.RestaurantModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRestaurantPersistencePort {
    RestaurantModel saveRestaurant(RestaurantModel restaurantModel);
    Page<RestaurantModel> findAllByOrderByName(Pageable pageable);

}
