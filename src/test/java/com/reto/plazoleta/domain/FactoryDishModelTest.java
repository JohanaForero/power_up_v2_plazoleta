package com.reto.plazoleta.domain;

import com.reto.plazoleta.domain.model.DishModel;

public class FactoryDishModelTest {
    public static DishModel dishModel() {
        DishModel dishModel = new DishModel();
        dishModel.setIdDish(1L);
        dishModel.setName("Swchih");
        dishModel.setPrice(1.100);
        dishModel.setImageDish("http://sabroson.img");
        dishModel.setStateDish("salado");
        return dishModel;
    }
}
