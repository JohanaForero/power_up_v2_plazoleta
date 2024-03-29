package com.reto.plazoleta.infraestructure.configuration;

import com.reto.plazoleta.domain.api.IEmployeeRestaurantServicePort;
import com.reto.plazoleta.domain.gateways.IUserGateway;
import com.reto.plazoleta.domain.spi.*;
import com.reto.plazoleta.domain.usecase.EmployeeRestaurantUseCase;
import com.reto.plazoleta.infraestructure.configuration.security.jwt.JwtProvider;
import com.reto.plazoleta.infraestructure.drivenadapter.mapper.IEmployeeEntityMapper;
import com.reto.plazoleta.infraestructure.drivenadapter.persistence.EmployeeRestaurantJpaAdapter;
import com.reto.plazoleta.infraestructure.drivenadapter.repository.IEmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class EmployeeBeanConfiguration {

    private final IEmployeeRepository employeeRepository;
    private final IEmployeeEntityMapper employeeEntityMapper;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IUserGateway userGateway;
    private final JwtProvider jwtProvider;
    private final IOrderPersistencePort orderPersistencePort;
    private final IToken token;

    @Bean
    public IEmployeeRestaurantPersistencePort employeeRestaurantPersistencePort() {
        return new EmployeeRestaurantJpaAdapter(this.employeeRepository, this.employeeEntityMapper);
    }

    @Bean
    public IEmployeeRestaurantServicePort employeeRestaurantServicePort() {
        return new EmployeeRestaurantUseCase(employeeRestaurantPersistencePort(), this.restaurantPersistencePort, this.userGateway,
                this.jwtProvider, token, this.orderPersistencePort);
    }
}