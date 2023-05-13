package com.reto.plazoleta.infraestructure.drivenadapter.persistence;

import com.reto.plazoleta.domain.model.DishModel;
import com.reto.plazoleta.domain.spi.IDishPersistencePort;
import com.reto.plazoleta.infraestructure.drivenadapter.entity.CategoryEntity;
import com.reto.plazoleta.infraestructure.drivenadapter.entity.DishEntity;
import com.reto.plazoleta.infraestructure.drivenadapter.entity.RestaurantEntity;
import com.reto.plazoleta.infraestructure.drivenadapter.mapper.IDishEntityMapper;
import com.reto.plazoleta.infraestructure.drivenadapter.repository.IDishRepository;

public class DishJpaAdapter implements IDishPersistencePort {
    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;


    public DishJpaAdapter(IDishRepository restaurantRepository, IDishEntityMapper restaurantEntityMapper) {
        this.dishRepository = restaurantRepository;
        this.dishEntityMapper = restaurantEntityMapper;
    }

    @Override
    public DishModel saveDish(DishModel dishModel) {
        RestaurantEntity restaurantEntity = new RestaurantEntity(dishModel.getRestaurantModel().getIdRestaurant(),
                dishModel.getRestaurantModel().getName(), dishModel.getRestaurantModel().getAddress(),
                dishModel.getRestaurantModel().getPhone(), dishModel.getRestaurantModel().getUrlLogo(),
                dishModel.getRestaurantModel().getNit(), dishModel.getRestaurantModel().getIdOwner());

        CategoryEntity categoryEntity = new CategoryEntity(dishModel.getCategoryModel().getIdCategory(),
                dishModel.getCategoryModel().getName(), dishModel.getCategoryModel().getDescription());

        DishEntity dishEntity = new DishEntity(dishModel.getIdDish(), dishModel.getName(), dishModel.getDescriptionDish(),
                dishModel.getPrice(), dishModel.getImageDish(), dishModel.getStateDish(), restaurantEntity, categoryEntity);
        return dishEntityMapper.toDishModel(dishRepository.save(dishEntity));
    }

}
