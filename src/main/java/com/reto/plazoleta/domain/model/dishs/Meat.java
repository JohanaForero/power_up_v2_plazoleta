package com.reto.plazoleta.domain.model.dishs;

import com.reto.plazoleta.domain.model.CategoryModel;
import com.reto.plazoleta.domain.model.RestaurantModel;


public class Meat extends DishModel {

    private int grams;

    public Meat(Long idDish, Integer grams) {
        super.setIdDish(idDish);
        this.grams = grams;
    }

    public int getGrams() {
        return grams;
    }

    public void setGrams(int grams) {
        this.grams = grams;
    }

    public Meat(){}

    public Meat(int grams) {
        this.grams = grams;
    }

    public Meat(Long idDish, String name, String descriptionDish, Double price,
                String imageDish, Boolean stateDish, RestaurantModel restaurantModel,
                CategoryModel categoryModel) {
        super(idDish, name, descriptionDish, price, imageDish, stateDish, restaurantModel, categoryModel);
    }

    public Meat(Long idDish, String name, String descriptionDish, Double price,
                String imageDish, Boolean stateDish, RestaurantModel restaurantModel,
                CategoryModel categoryModel, Integer grams) {
        super(idDish, name, descriptionDish, price, imageDish, stateDish, restaurantModel, categoryModel);
        this.grams = grams;
    }
}
