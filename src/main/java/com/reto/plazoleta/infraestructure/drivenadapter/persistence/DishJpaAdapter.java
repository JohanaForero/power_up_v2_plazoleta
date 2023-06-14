package com.reto.plazoleta.infraestructure.drivenadapter.persistence;

import com.reto.plazoleta.domain.model.DishModel;
import com.reto.plazoleta.domain.spi.IDishPersistencePort;
import com.reto.plazoleta.infraestructure.drivenadapter.mapper.IDishEntityMapper;
import com.reto.plazoleta.infraestructure.drivenadapter.repository.IDishRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class DishJpaAdapter implements IDishPersistencePort {
    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;

    public DishJpaAdapter(IDishRepository restaurantRepository, IDishEntityMapper restaurantEntityMapper) {
        this.dishRepository = restaurantRepository;
        this.dishEntityMapper = restaurantEntityMapper;
    }

    @Override
    public DishModel saveDish(DishModel dishModel) {
        return dishEntityMapper.toDishModel(dishRepository.save(dishEntityMapper.toDishEntity(dishModel)));
    }

    @Override
    public DishModel updateDish(DishModel dishModel) {
        return  dishEntityMapper.toDishModel(
                dishRepository.save(dishEntityMapper.toDishEntity(dishModel)));
    }

    @Override
    public Page<DishModel> findByRestaurantIdPaginated(Long restaurantId, Pageable pageable) {
        return this.dishRepository
                .findByRestaurantEntityIdRestaurantOrderByCategoryEntity(restaurantId, pageable)
                .map(dishEntityMapper::toDishModel);
    }

    @Override
    public DishModel findById(Long idDish) {
        return dishEntityMapper.toDishModel(dishRepository.findById(idDish).orElse(null));
    }
}