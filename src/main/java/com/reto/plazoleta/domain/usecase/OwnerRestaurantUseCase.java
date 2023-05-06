package com.reto.plazoleta.domain.usecase;

import com.reto.plazoleta.domain.api.IOwnerRestaurantServicePort;
import com.reto.plazoleta.domain.model.DishModel;
import com.reto.plazoleta.domain.spi.IDishPersistencePort;

public class OwnerRestaurantUseCase implements IOwnerRestaurantServicePort {

    private final IDishPersistencePort dishPersistencePort;

    public OwnerRestaurantUseCase(IDishPersistencePort persistencePort) {
        this.dishPersistencePort = persistencePort;
    }

    @Override
    public void saveDish(DishModel dishModel) {
        dishPersistencePort.saveDish(dishModel);
    }

}
