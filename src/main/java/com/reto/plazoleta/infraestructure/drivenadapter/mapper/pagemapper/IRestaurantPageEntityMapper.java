package com.reto.plazoleta.infraestructure.drivenadapter.mapper.pagemapper;

import com.reto.plazoleta.domain.model.RestaurantModel;
import com.reto.plazoleta.infraestructure.drivenadapter.entity.RestaurantEntity;
import org.springframework.data.domain.Page;

public interface IRestaurantPageEntityMapper {
    Page<RestaurantModel> toRestaurantModelPage(Page<RestaurantEntity> restaurantEntity);
}
