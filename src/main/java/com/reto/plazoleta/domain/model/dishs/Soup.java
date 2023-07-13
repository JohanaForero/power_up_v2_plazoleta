package com.reto.plazoleta.domain.model.dishs;

import com.reto.plazoleta.domain.model.CategoryModel;
import com.reto.plazoleta.domain.model.RestaurantModel;

public class Soup extends DishModel {

    private String accompaniment;

    public String getAccompaniment() {
        return accompaniment;
    }

    public void setAccompaniment(String accompaniment) {
        this.accompaniment = accompaniment;
    }

    public Soup(String accompaniment) {
        this.accompaniment = accompaniment;
    }

    public Soup(Long idDish, String name, String descriptionDish, Double price, String imageDish, Boolean stateDish, RestaurantModel restaurantModel, CategoryModel categoryModel, String accompaniment) {
        super(idDish, name, descriptionDish, price, imageDish, stateDish, restaurantModel, categoryModel);
        this.accompaniment = accompaniment;
    }
}