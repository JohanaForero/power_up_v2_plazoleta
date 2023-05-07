package com.reto.plazoleta.domain.usecase;

import com.reto.plazoleta.domain.api.IOwnerRestaurantServicePort;
import com.reto.plazoleta.domain.exception.DishNotExistsException;
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

    public void updateDish(DishModel dishModel) {
        DishModel update = dishPersistencePort.findById(dishModel.getIdDish());
        if(update == null) {
            throw new DishNotExistsException("The dish does not exist");
        }
        update.setIdDish(dishModel.getIdDish());
        update.setDescriptionDish(dishModel.getDescriptionDish());
        update.setPrice(dishModel.getPrice());
        dishPersistencePort.updateDish(update);
    }


}
