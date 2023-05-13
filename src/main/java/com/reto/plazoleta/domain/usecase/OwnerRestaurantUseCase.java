package com.reto.plazoleta.domain.usecase;

import com.reto.plazoleta.domain.api.IOwnerRestaurantServicePort;
import com.reto.plazoleta.domain.exception.InvalidDataException;
import com.reto.plazoleta.domain.model.CategoryModel;
import com.reto.plazoleta.domain.model.DishModel;
import com.reto.plazoleta.domain.model.RestaurantModel;
import com.reto.plazoleta.domain.spi.ICategoryPersistencePort;
import com.reto.plazoleta.domain.spi.IDishPersistencePort;
import com.reto.plazoleta.domain.spi.IRestaurantPersistencePort;

public class OwnerRestaurantUseCase implements IOwnerRestaurantServicePort {

    private final IDishPersistencePort dishPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final ICategoryPersistencePort categoryPersistencePort;

    public OwnerRestaurantUseCase(IDishPersistencePort persistencePort, IRestaurantPersistencePort restaurantPersistencePort,
                                  ICategoryPersistencePort categoryPersistencePort) {
        this.dishPersistencePort = persistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public void saveDish(DishModel dishModel, Long idCategory, Long idRestaurant) {
        if(dishModel.getPrice() < 1) {
            throw new InvalidDataException("Price must be greater than zero");
        }
        RestaurantModel restaurantModel = restaurantPersistencePort.findByIdRestaurant(idRestaurant);
        if(restaurantModel == null) {
            throw new InvalidDataException("The restaurant does not exist");
        }
        CategoryModel categoryModel = categoryPersistencePort.findById(idCategory);
        if(categoryModel == null) {
            throw new InvalidDataException("The category does not exist");
        }
        dishModel.setRestaurantModel(restaurantModel);
        dishModel.setCategoryModel(categoryModel);
        dishModel.setStateDish(true);
        dishPersistencePort.saveDish(dishModel);
    }

}
