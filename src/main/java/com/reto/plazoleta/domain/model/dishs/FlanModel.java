package com.reto.plazoleta.domain.model.dishs;

import com.reto.plazoleta.domain.model.CategoryModel;
import com.reto.plazoleta.domain.model.RestaurantModel;

public class FlanModel extends Desserts {

    private String topping;

    public FlanModel() {
    }

    public FlanModel(String topping) {
        this.topping = topping;
    }

    public FlanModel(Long idDish, String name, String descriptionDish, Double price,
                     String imageDish, Boolean stateDish, RestaurantModel restaurantModel,
                     CategoryModel categoryModel) {
        super(idDish, name, descriptionDish, price, imageDish, stateDish, restaurantModel, categoryModel);
    }

    public FlanModel(Long idDish, String name, String descriptionDish, Double price,
                     String imageDish, Boolean stateDish, RestaurantModel restaurantModel,
                     CategoryModel categoryModel, String topping) {
        super(idDish, name, descriptionDish, price, imageDish, stateDish, restaurantModel, categoryModel);
        this.topping = topping;
    }

    public String getTopping() {
        return topping;
    }

    public void setTopping(String topping) {
        this.topping = topping;
    }
}
