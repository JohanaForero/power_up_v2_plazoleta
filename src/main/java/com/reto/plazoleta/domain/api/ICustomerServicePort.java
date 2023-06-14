package com.reto.plazoleta.domain.api;

import com.reto.plazoleta.domain.model.DishModel;
import org.springframework.data.domain.Page;

public interface ICustomerServicePort {

    Page<DishModel> findAllDishByIdRestaurantAndGroupByCategoryPaginated(Integer numberPage, Integer sizeItems, Long idRestaurant);
}
