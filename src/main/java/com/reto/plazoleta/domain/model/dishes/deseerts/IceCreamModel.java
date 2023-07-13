package com.reto.plazoleta.domain.model.dishes.deseerts;

import com.reto.plazoleta.domain.model.CategoryModel;
import com.reto.plazoleta.domain.model.RestaurantModel;

public class IceCreamModel extends Desserts {

    private String flavor;


    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public IceCreamModel() {
    }

    public IceCreamModel(String flavor) {
        this.flavor = flavor;
    }

    public IceCreamModel(Long idDish, String name, String descriptionDish, Double price,
                               String imageDish, Boolean stateDish, RestaurantModel restaurantModel,
                               CategoryModel categoryModel) {
        super(idDish, name, descriptionDish, price, imageDish, stateDish, restaurantModel, categoryModel);
    }

    public IceCreamModel(Long idDish, String name, String descriptionDish, Double price,
                               String imageDish, Boolean stateDish, RestaurantModel restaurantModel,
                               CategoryModel categoryModel, String flavor) {
        super(idDish, name, descriptionDish, price, imageDish, stateDish, restaurantModel, categoryModel);
        this.flavor = flavor;
    }
}
