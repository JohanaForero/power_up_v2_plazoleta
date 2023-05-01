package com.reto.plazoleta.domain.spi;

import com.reto.plazoleta.domain.model.DishModel;

import java.util.List;

public interface IDishPersistencePort {
    DishModel saveDish(DishModel dishModel);


}
