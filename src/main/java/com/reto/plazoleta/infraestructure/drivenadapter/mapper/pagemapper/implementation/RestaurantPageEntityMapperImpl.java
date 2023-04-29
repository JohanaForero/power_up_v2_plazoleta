package com.reto.plazoleta.infraestructure.drivenadapter.mapper.pagemapper.implementation;

import com.reto.plazoleta.domain.model.RestaurantModel;
import com.reto.plazoleta.infraestructure.drivenadapter.entity.RestaurantEntity;
import com.reto.plazoleta.infraestructure.drivenadapter.mapper.pagemapper.IRestaurantPageEntityMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RestaurantPageEntityMapperImpl implements IRestaurantPageEntityMapper {

    @Override
    public Page<RestaurantModel> toRestaurantModelPage(Page<RestaurantEntity> restaurantsEntityPage) {
        return restaurantsEntityPage.map(new Function<RestaurantEntity, RestaurantModel>() {
            @Override
            public RestaurantModel apply(RestaurantEntity restaurantEntity) {
                RestaurantModel restaurantModel = new RestaurantModel();
                restaurantModel.setIdRestaurant(restaurantEntity.getIdRestaurant());
                restaurantModel.setName(restaurantEntity.getName());
                restaurantModel.setUrlLogo(restaurantEntity.getUrlLogo());
                restaurantModel.setAddress(restaurantEntity.getAddress());
                restaurantModel.setPhone(restaurantEntity.getPhone());
                restaurantModel.setIdOwner(restaurantEntity.getIdOwner());
                restaurantModel.setNit(restaurantEntity.getNit());
                return restaurantModel;
            }
        });
    }
}

