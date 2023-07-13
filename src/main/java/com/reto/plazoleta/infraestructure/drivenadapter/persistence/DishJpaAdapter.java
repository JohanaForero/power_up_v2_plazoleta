package com.reto.plazoleta.infraestructure.drivenadapter.persistence;

import com.reto.plazoleta.domain.model.dishes.DishModel;
import com.reto.plazoleta.domain.spi.IDishPersistencePort;
import com.reto.plazoleta.infraestructure.drivenadapter.mapper.IDishEntityMapper;
import com.reto.plazoleta.infraestructure.drivenadapter.repository.IDishRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.stream.Collectors;

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
    public DishModel findById(Long idDish) {
        return dishEntityMapper.toDishModel(dishRepository.findById(idDish).orElse(null));
    }

    @Override
    public Page<DishModel> getAllDishesActiveOfARestaurantOrderByCategoryAscending(Pageable pageable, Long idRestaurant) {
        return this.dishRepository.findByRestaurantEntityIdRestaurantOrderByCategoryEntityAsc(pageable, idRestaurant)
                .filter(dishModel -> dishModel.getStateDish().equals(true)).map(this.dishEntityMapper::toDishModel)
                .stream().collect(Collectors.collectingAndThen(Collectors.toList(), PageImpl::new));
    }
}