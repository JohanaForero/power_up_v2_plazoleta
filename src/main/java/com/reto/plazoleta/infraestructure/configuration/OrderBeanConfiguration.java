package com.reto.plazoleta.infraestructure.configuration;

import com.reto.plazoleta.domain.api.ICustomerServicePort;
import com.reto.plazoleta.domain.gateways.IUserGateway;
import com.reto.plazoleta.domain.spi.*;
import com.reto.plazoleta.domain.usecase.CustomerUseCase;
import com.reto.plazoleta.infraestructure.configuration.security.jwt.JwtProvider;
import com.reto.plazoleta.infraestructure.drivenadapter.mapper.IOrderEntityMapper;
import com.reto.plazoleta.infraestructure.drivenadapter.persistence.OrderDishJpaAdapter;
import com.reto.plazoleta.infraestructure.drivenadapter.persistence.OrderJpaAdapter;
import com.reto.plazoleta.infraestructure.drivenadapter.persistence.TokenAdapter;
import com.reto.plazoleta.infraestructure.drivenadapter.repository.IOrderDishRepository;
import com.reto.plazoleta.infraestructure.drivenadapter.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class OrderBeanConfiguration {

    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;
    private final IUserGateway userGateway;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IDishPersistencePort dishPersistencePort;
    private final JwtProvider jwtProvider;
    private final IOrderDishRepository orderDishRepository;
    private final IToken token;

    @Bean
    public IOrderPersistencePort orderPersistencePort() {
        return new OrderJpaAdapter(this.orderRepository, this.orderEntityMapper,orderDishRepository);
    }

    @Bean
    public IOrderDishPersistencePort orderDishPersistencePort() {
        return new OrderDishJpaAdapter(this.orderDishRepository, this.orderEntityMapper);
    }

    @Bean
    public ICustomerServicePort orderServicePort() {
        return new CustomerUseCase(this.orderPersistencePort(), this.restaurantPersistencePort, this.dishPersistencePort,
                this.userGateway, this.jwtProvider, orderDishPersistencePort(), token);
    }
}