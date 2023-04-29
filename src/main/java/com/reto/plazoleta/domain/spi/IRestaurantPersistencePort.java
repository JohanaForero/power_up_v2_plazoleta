package com.reto.plazoleta.domain.spi;

import com.reto.plazoleta.domain.model.RestaurantModel;

public interface IRestaurantPersistencePort {

    RestaurantModel saveRestaurant(RestaurantModel restaurantModel);
}
