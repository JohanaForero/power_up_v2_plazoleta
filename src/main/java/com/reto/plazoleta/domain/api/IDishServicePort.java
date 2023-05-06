package com.reto.plazoleta.domain.api;

import com.reto.plazoleta.domain.model.DishModel;

import java.util.List;

public interface IDishServicePort {

    void saveDish(DishModel dishModel);

    List<DishModel> getAllDishs();


}
