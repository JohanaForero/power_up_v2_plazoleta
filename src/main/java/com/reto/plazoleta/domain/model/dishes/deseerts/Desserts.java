package com.reto.plazoleta.domain.model.dishes.deseerts;

import com.reto.plazoleta.domain.model.CategoryModel;
import com.reto.plazoleta.domain.model.RestaurantModel;
import com.reto.plazoleta.domain.model.dishes.DishModel;

public class Desserts extends DishModel  {

    private String dessertType;

    public Desserts(Long idDish, String dessertType, String descriptionDish, Double price, String imageDish, Boolean stateDish, RestaurantModel restaurantModel, CategoryModel categoryModel) {
        this.dessertType = dessertType;
    }

    public Desserts(Long idDish, String name, String descriptionDish, Double price, String imageDish, Boolean stateDish, RestaurantModel restaurantModel, CategoryModel categoryModel, String dessertType) {
        super(idDish, name, descriptionDish, price, imageDish, stateDish, restaurantModel, categoryModel);
    }

    public Desserts(){}

    public String getDessertType() {
        return dessertType;
    }

    public void setDessertType(String dessertType) {
        this.dessertType = dessertType;
    }
}
