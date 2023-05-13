package com.reto.plazoleta.domain.usecase;

import com.reto.plazoleta.domain.api.IOwnerRestaurantServicePort;
import com.reto.plazoleta.domain.exception.DishNotExistsException;
import com.reto.plazoleta.domain.exception.RestrictedAccessException;
import com.reto.plazoleta.domain.model.CategoryModel;
import com.reto.plazoleta.domain.model.DishModel;
import com.reto.plazoleta.domain.model.RestaurantModel;
import com.reto.plazoleta.domain.spi.ICategoryPersistencePort;
import com.reto.plazoleta.domain.spi.IDishPersistencePort;
import com.reto.plazoleta.domain.spi.IRestaurantPersistencePort;

public class OwnerRestaurantUseCase implements IOwnerRestaurantServicePort {

    private final IDishPersistencePort dishPersistencePort;
    private final ICategoryPersistencePort categoryPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;

    public OwnerRestaurantUseCase(IDishPersistencePort persistencePort, ICategoryPersistencePort categoryPersistencePort, IRestaurantPersistencePort restaurantPersistencePort) {
        this.dishPersistencePort = persistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
    }

    @Override
    public void saveCategory(CategoryModel categoryModel) {
        /*CategoryModel saveCategory = categoryPersistencePort.findById(categoryModel.getIdCategory());
        if(categoryModel.getIdRestaurant() != saveCategory.getIdRestaurant()) {
            throw new RestrictedAccessException("This restaurant is not authorized to create the category of another restaurant.");
        }
        categoryPersistencePort.saveCategory(categoryModel);*/
    }

    @Override
    public void saveDish(DishModel dishModel) {
        CategoryModel categoryModel = categoryPersistencePort.findById(dishModel.getCategoryModel().getIdCategory());
        if(categoryModel == null) {
            throw new IllegalArgumentException("Id null category");
        }
        dishModel.setCategoryModel(categoryModel);
        RestaurantModel restaurantModel = restaurantPersistencePort.findByIdRestaurant(dishModel.getRestaurantModel().getIdRestaurant());
        if(restaurantModel == null) {
            throw new IllegalArgumentException("Id null restaurant");
        }
        dishModel.setRestaurantModel(restaurantModel);
        dishPersistencePort.saveDish(dishModel);
    }

    @Override
    public DishModel updateDish(DishModel dishModel) {
        DishModel update = dishPersistencePort.findById(dishModel.getIdDish());
        if (update == null) {
            throw new DishNotExistsException("The dish does not exist");
        }
        if(dishModel.getRestaurantModel().getIdRestaurant() != update.getRestaurantModel().getIdRestaurant()) {
            throw new RestrictedAccessException("This restaurant is not authorized to upgrade another restaurant's dish");
        }
        update.setIdDish(dishModel.getIdDish());
        update.setDescriptionDish(dishModel.getDescriptionDish());
        update.setPrice(dishModel.getPrice());
        return dishPersistencePort.updateDish(update);
    }

    @Override
    public DishModel updateDishState(DishModel dishModel) {
        DishModel update = dishPersistencePort.findById(dishModel.getIdDish());
        if (update == null) {
            throw new DishNotExistsException("The dish does not exist");
        }
        if(dishModel.getRestaurantModel().getIdRestaurant() != update.getRestaurantModel().getIdRestaurant()) {
            throw new RestrictedAccessException("This restaurant is not authorized to upgrade another restaurant's dish");
        }
        update.setIdDish(dishModel.getIdDish());
        update.setStateDish(dishModel.getStateDish());
        return dishPersistencePort.updateDishState(update);
    }

}
