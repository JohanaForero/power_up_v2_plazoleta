package com.reto.plazoleta.infraestructure.drivenadapter.persistence;

import com.reto.plazoleta.domain.model.RestaurantModel;
import com.reto.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.reto.plazoleta.infraestructure.drivenadapter.mapper.IRestaurantEntityMapper;
import com.reto.plazoleta.infraestructure.drivenadapter.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class RestaurantJpaAdapter implements IRestaurantPersistencePort {

    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;

    @Override
    public RestaurantModel saveRestaurant(RestaurantModel restaurantModel) {
        return restaurantEntityMapper.toRestaurantModel(
                restaurantRepository.save(restaurantEntityMapper.toRestaurantEntity(restaurantModel)));
    }

    @Override
    public Page<RestaurantModel> findAllByOrderByNameAsc(Pageable pageable) {
        return restaurantRepository.findAllByOrderByName(pageable).map(restaurantEntityMapper::toRestaurantModel);
    }
}
