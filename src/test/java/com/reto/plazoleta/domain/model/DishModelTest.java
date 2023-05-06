package com.reto.plazoleta.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DishModelTest {

    public static DishModel dishModel() {
        DishModel dishModel = new DishModel();
        dishModel.setIdDish(1L);
        dishModel.setName("Arroz Chino");
        dishModel.setPrice(15.000);
        dishModel.setDescriptionDish("arroz con camorenes");
        dishModel.setImageDish("http://sabroson.img");
        dishModel.setStateDish(true);
        return dishModel;
    }

    public static DishModel dishModelExpected() {
        DishModel dishModelExpected = new DishModel();
        dishModelExpected.setIdDish(1L);
        dishModelExpected.setName("Arroz Chino");
        dishModelExpected.setPrice(10.000);
        dishModelExpected.setDescriptionDish("Arroz con Camarones");
        dishModelExpected.setImageDish("http://sabroson.img");
        dishModelExpected.setStateDish(true);
        return dishModelExpected;
    }

    public static DishModel dishModelActual() {
        DishModel dishModelActual = new DishModel();
        dishModelActual.setIdDish(1L);
        dishModelActual.setName("Arroz Chino");
        dishModelActual.setPrice(15.000);
        dishModelActual.setDescriptionDish("arroz con camorenes");
        dishModelActual.setImageDish("http://sabroson.img");
        dishModelActual.setStateDish(true);
        return dishModelActual;
    }

    public static DishModel dishModel2() {
        DishModel dishModel2 = new DishModel();
        dishModel2.setIdDish(2L);
        dishModel2.setName("Lazaña");
        dishModel2.setPrice(1.2000);
        dishModel2.setImageDish("https://images.app.goo.gl/JeePJJ7SstyqrB5a8");
        dishModel2.setStateDish(true);
        return dishModel2;
    }
    public static DishModel dishModel3() {
        DishModel dishModel3 = new DishModel();
        dishModel3.setIdDish(3L);
        dishModel3.setName("pabellon");
        dishModel3.setPrice(1.00);
        dishModel3.setImageDish("https://imagen.pabellon.img");
        dishModel3.setStateDish(true);
        return dishModel3;
    }

}