package com.reto.plazoleta.infraestructure.configuration;

import com.reto.plazoleta.domain.api.IEmployeeRestaurantServicePort;
import com.reto.plazoleta.domain.spi.IEmployeeRestaurantPersistencePort;
import com.reto.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.reto.plazoleta.domain.usecase.EmployeeRestaurantUseCase;
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

    @Bean
    public IEmployeeRestaurantPersistencePort employeeRestaurantPersistencePort() {
        return new EmployeeRestaurantJpaAdapter(employeeRepository, employeeEntityMapper);
    }

    @Bean
    public IEmployeeRestaurantServicePort employeeRestaurantServicePort() {
        return new EmployeeRestaurantUseCase(employeeRestaurantPersistencePort(), restaurantPersistencePort);
    }
}
