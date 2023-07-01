package com.reto.plazoleta.domain.api;

import com.reto.plazoleta.domain.model.dishes.DishModel;
import com.reto.plazoleta.domain.model.OrderModel;
import org.springframework.data.domain.Page;

public interface ICustomerServicePort {

    OrderModel saveOrder(OrderModel orderModelRequest, String tokenWithPrefixBearer);

    OrderModel orderCanceled(Long idOrder, String tokenWithPrefixBearer);

    Page<DishModel> getAllDishesActivePaginatedFromARestaurantOrderByCategoryAscending(Integer numberPage, Integer sizeItems, Long idRestaurant);
}
