package com.reto.plazoleta.domain.spi;

import com.reto.plazoleta.domain.model.dishs.DishModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IDishPersistencePort {

    DishModel saveDish(DishModel dishModel);

    DishModel findById(Long idDish);

    DishModel updateDish(DishModel existingDishModel);

    Page<DishModel> getAllDishesActiveOfARestaurantOrderByCategoryAscending(Pageable pageable, Long idRestaurant);
}
