package com.reto.plazoleta.domain.api;

import com.reto.plazoleta.domain.model.OrderModel;

public interface ICustomerServicePort {

    OrderModel saveOrder(OrderModel orderModelRequest, String tokenWithPrefixBearer);

    OrderModel orderCanceled(Long idOrder, String tokenWithPrefixBearer);
}
