package com.reto.plazoleta.domain.model.dishs;


import com.reto.plazoleta.domain.model.CategoryModel;
import com.reto.plazoleta.domain.model.RestaurantModel;

public class Desserts extends DishModel {

    public Desserts() {
    }

    public Desserts(Long idDish, String name, String descriptionDish, Double price,
                    String imageDish, Boolean stateDish, RestaurantModel restaurantModel,
                    CategoryModel categoryModel) {
        super(idDish, name, descriptionDish, price, imageDish, stateDish, restaurantModel, categoryModel);
    }
}
