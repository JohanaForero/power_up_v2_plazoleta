package com.reto.plazoleta.domain.api;

import com.reto.plazoleta.domain.model.DishModel;
import org.springframework.data.domain.Page;
import com.reto.plazoleta.domain.model.OrderModel;

public interface ICustomerServicePort {

    Page<DishModel> findAllDishByIdRestaurantAndGroupByCategoryPaginated(Integer numberPage, Integer sizeItems, Long idRestaurant);

    OrderModel saveOrder(OrderModel orderModelRequest, String tokenWithPrefixBearer);

    OrderModel orderCanceled(Long idOrder, String tokenWithPrefixBearer);
}
