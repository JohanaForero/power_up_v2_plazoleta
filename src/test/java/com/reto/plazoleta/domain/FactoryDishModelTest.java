package com.reto.plazoleta.domain;

import com.reto.plazoleta.domain.model.DishModel;

public class FactoryDishModelTest {

    public static DishModel dishModel() {
        DishModel dishModel = new DishModel();
        dishModel.setIdDish(1L);
        dishModel.setName("Arroz Chino");
        dishModel.setPrice(15.000);
        dishModel.setDescriptionDish("arroz con camorenes");
        dishModel.setImageDish("http://sabroson.img");
        dishModel.setStateDish("activo");
        return dishModel;
    }

    public static DishModel dishModelExpected() {
        DishModel dishModelExpected = new DishModel();
        dishModelExpected.setIdDish(1L);
        dishModelExpected.setName("Arroz Chino");
        dishModelExpected.setPrice(10.000);
        dishModelExpected.setDescriptionDish("Arroz con Camarones");
        dishModelExpected.setImageDish("http://sabroson.img");
        dishModelExpected.setStateDish("activo");
        return dishModelExpected;
    }

    public static DishModel dishModelActual() {
        DishModel dishModelActual = new DishModel();
        dishModelActual.setIdDish(1L);
        dishModelActual.setName("Arroz Chino");
        dishModelActual.setPrice(15.000);
        dishModelActual.setDescriptionDish("arroz con camorenes");
        dishModelActual.setImageDish("http://sabroson.img");
        dishModelActual.setStateDish("activo");
        return dishModelActual;
    }
}
