package com.reto.plazoleta.domain.api;

import com.reto.plazoleta.domain.model.DishModel;

public interface IOwnerRestaurantServicePort {

    void saveDish(DishModel dishModel, Long idCategory, Long idRestaurant);
}
