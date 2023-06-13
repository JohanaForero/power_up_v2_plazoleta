package com.reto.plazoleta.infraestructure.configuration;

import com.reto.plazoleta.domain.api.IOwnerRestaurantServicePort;
import com.reto.plazoleta.domain.gateways.IUserGateway;
import com.reto.plazoleta.domain.spi.ICategoryPersistencePort;
import com.reto.plazoleta.domain.spi.IDishPersistencePort;
import com.reto.plazoleta.domain.spi.IEmployeeRestaurantPersistencePort;
import com.reto.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.reto.plazoleta.domain.usecase.OwnerRestaurantUseCase;
import com.reto.plazoleta.infraestructure.configuration.security.jwt.JwtProvider;
import com.reto.plazoleta.infraestructure.drivenadapter.mapper.IEmployeeEntityMapper;
import com.reto.plazoleta.infraestructure.drivenadapter.persistence.EmployeeRestaurantJpaAdapter;
import com.reto.plazoleta.infraestructure.drivenadapter.repository.IEmployeeRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class EmployeeBeanConfiguration {

    private final IEmployeeRepository employeeRepository;
    private final IEmployeeEntityMapper employeeEntityMapper;
    private final IDishPersistencePort dishPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final ICategoryPersistencePort categoryPersistencePort;
    private final IUserGateway userGateway;
    private final JwtProvider jwtProvider;

    @Bean
    public IEmployeeRestaurantPersistencePort employeeRestaurantPersistencePort() {
        return new EmployeeRestaurantJpaAdapter(employeeRepository,employeeEntityMapper);
    }

    @Bean
    public IOwnerRestaurantServicePort ownerRestaurantServicePort() {
        return new OwnerRestaurantUseCase(dishPersistencePort, restaurantPersistencePort, categoryPersistencePort,
                employeeRestaurantPersistencePort(), userGateway, jwtProvider);
    }

}
