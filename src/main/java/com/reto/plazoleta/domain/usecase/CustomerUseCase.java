package com.reto.plazoleta.domain.usecase;

import com.reto.plazoleta.domain.api.ICustomerServicePort;
import com.reto.plazoleta.domain.exception.ObjectNotFoundException;
import com.reto.plazoleta.domain.model.DishModel;
import com.reto.plazoleta.domain.model.RestaurantModel;
import com.reto.plazoleta.domain.spi.IDishPersistencePort;
import com.reto.plazoleta.domain.spi.IRestaurantPersistencePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public class CustomerUseCase implements ICustomerServicePort {

    private final IDishPersistencePort dishPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;

    public CustomerUseCase(IDishPersistencePort dishPersistencePort, IRestaurantPersistencePort restaurantPersistencePort) {
        this.dishPersistencePort = dishPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
    }

    @Override
    public Page<DishModel> findAllDishByIdRestaurantAndGroupByCategoryPaginated(Integer numberPage, Integer sizeItems, Long idRestaurant) {
        RestaurantModel restaurant = restaurantPersistencePort.findByIdRestaurant(idRestaurant);
        if (restaurant == null) {
            throw new ObjectNotFoundException("No se encontró el restaurante con el ID especificado.");
        }
        Page<DishModel> dishesOrderByCategory = this.dishPersistencePort.findByRestaurantIdPaginated(idRestaurant, PageRequest.of(numberPage, sizeItems));
        if (dishesOrderByCategory.isEmpty()) {
            throw new ObjectNotFoundException("No se encontraron platos para el restaurante especificado.");
        }
        return dishesOrderByCategory;
    }
}
