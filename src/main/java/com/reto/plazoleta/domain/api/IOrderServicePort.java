package com.reto.plazoleta.domain.api;

import com.reto.plazoleta.domain.model.OrderModel;

import java.util.List;

public interface IOrderServicePort {
    void saveOrder(OrderModel orderModel);
    List<OrderModel> getAllOrders();

}
