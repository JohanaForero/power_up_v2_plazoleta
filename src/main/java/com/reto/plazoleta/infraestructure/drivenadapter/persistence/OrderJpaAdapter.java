package com.reto.plazoleta.infraestructure.drivenadapter.persistence;

import com.reto.plazoleta.domain.model.OrderModel;
import com.reto.plazoleta.domain.spi.IOrderPersistencePort;
import com.reto.plazoleta.infraestructure.drivenadapter.entity.OrderEntity;
import com.reto.plazoleta.infraestructure.drivenadapter.entity.StatusOrder;
import com.reto.plazoleta.infraestructure.drivenadapter.mapper.OrderMapperPersistence;
import com.reto.plazoleta.infraestructure.drivenadapter.mapper.IOrderEntityMapper;
import com.reto.plazoleta.infraestructure.drivenadapter.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class OrderJpaAdapter implements IOrderPersistencePort {

    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;
    private OrderMapperPersistence orderMapperPersistence;

    @Override
    public OrderModel saveOrder(OrderModel orderModel) {
        final OrderEntity orderEntityRequest = this.orderEntityMapper.toOrderEntity(orderModel);
        final OrderEntity orderEntitySaved = this.orderRepository.save(orderEntityRequest);
        return this.orderMapperPersistence.convertOrderEntityToOrderModel(orderEntitySaved);
    }

    @Override
    public List<OrderModel> findByIdUserCustomerAndIdRestaurant(Long idUser, Long idRestaurant) {
        return orderRepository.findByIdUserCustomerAndRestaurantEntityIdRestaurant(idUser, idRestaurant).stream()
                .map(orderEntityMapper::toOrderModel).collect(Collectors.toList());
    }

    @Override
    public Page<OrderModel> findAllByRestaurantEntityIdRestaurantAndStatusOrder(Pageable pageable, Long idRestaurant, StatusOrder status) {
        return this.orderRepository.findAllByRestaurantEntityIdRestaurantAndStatus(pageable, idRestaurant, status).map(orderEntityMapper::toOrderModel);
    }

    @Override
    public OrderModel findByIdOrder(Long idOrder) {
        return this.orderEntityMapper.toOrderModel(this.orderRepository.findById(idOrder).orElse(null));
    }
    @Override
    public List<OrderModel> findAllOrderByRestaurantIdAndStatusOrderEarring(Long idRestaurant) {
        return this.orderRepository.findAllByRestaurantEntityIdRestaurantAndStatus(idRestaurant, StatusOrder.PENDIENTE)
                .stream().map(orderEntity -> orderMapperPersistence.convertOrderEntityToOrderModel(orderEntity))
                .collect(Collectors.toList());
    }
}
