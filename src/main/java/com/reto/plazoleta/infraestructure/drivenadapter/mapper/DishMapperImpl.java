package com.reto.plazoleta.infraestructure.drivenadapter.mapper;

import com.reto.plazoleta.domain.model.dishes.DishModel;
import com.reto.plazoleta.infraestructure.drivenadapter.entity.DishEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DishMapperImpl {

    public static DishModel dishMapperToDishModel(DishEntity dishEntity) {
        /*CategoryModel categoryResponse = new CategoryModel(dishEntity.getCategoryEntity().getIdCategory(),
                dishEntity.getCategoryEntity().getName(), dishEntity.getCategoryEntity().getDescription());
        RestaurantModel restaurantModel = new RestaurantModel();*/
        DishModel dishResponse = new DishModel();
        dishResponse.setIdDish(dishEntity.getIdDish());
        dishResponse.setDescriptionDish(dishEntity.getDescriptionDish());
        dishResponse.setImageDish(dishEntity.getImageDish());
        dishResponse.setName(dishEntity.getName());
        dishResponse.setPrice(dishEntity.getPrice());
        dishResponse.setStateDish(dishEntity.getStateDish());

        return dishResponse;
    }
}

