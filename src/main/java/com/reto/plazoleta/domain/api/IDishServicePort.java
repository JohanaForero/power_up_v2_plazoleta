package com.reto.plazoleta.domain.api;

import com.reto.plazoleta.domain.model.DishModel;

import java.util.List;

public interface IDishServicePort {

    List<DishModel> getAllDishs();
    void saveDish(DishModel dishModel);
}
