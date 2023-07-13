package com.reto.plazoleta.domain.model.dishes.deseerts;

import com.reto.plazoleta.domain.model.CategoryModel;
import com.reto.plazoleta.domain.model.RestaurantModel;

public class FlanModel extends Desserts {

    private String accompaniment;

    public FlanModel(Long idDish, String name, String descriptionDish, Double price, String imageDish, Boolean stateDish, RestaurantModel restaurantModel, CategoryModel categoryModel, String dessertType) {
        super(idDish, name, descriptionDish, price, imageDish, stateDish, restaurantModel, categoryModel, dessertType);
    }


    public String getAccompaniment() {
        return accompaniment;
    }

    public void setAccompaniment(String accompaniment) {
        this.accompaniment = accompaniment;
    }
}
