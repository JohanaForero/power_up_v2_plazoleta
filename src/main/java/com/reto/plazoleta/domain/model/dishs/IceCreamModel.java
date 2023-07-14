package com.reto.plazoleta.domain.model.dishs;

import com.reto.plazoleta.domain.model.CategoryModel;
import com.reto.plazoleta.domain.model.RestaurantModel;

public class IceCreamModel extends Desserts {

    private String flavor;

    public IceCreamModel() {
        super();
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

    public IceCreamModel(Long idDish, String flavor) {
        super.setIdDish(idDish);
        this.flavor = flavor;
    }

    public String getFlavor() {
        return flavor;
    }

}
