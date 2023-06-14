package com.reto.plazoleta.infraestructure.configuration;


import com.reto.plazoleta.domain.api.ICustomerServicePort;
import com.reto.plazoleta.domain.spi.ICategoryPersistencePort;
import com.reto.plazoleta.domain.spi.IDishPersistencePort;
import com.reto.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.reto.plazoleta.domain.usecase.CustomerUseCase;
import com.reto.plazoleta.infraestructure.drivenadapter.mapper.IDishEntityMapper;
import com.reto.plazoleta.infraestructure.drivenadapter.persistence.DishJpaAdapter;
import com.reto.plazoleta.infraestructure.drivenadapter.repository.IDishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DishBeanConfiguration {

    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;
    private final IRestaurantPersistencePort restaurantPersistencePort;

    @Bean
    public IDishPersistencePort dishPersistencePort() {
        return new DishJpaAdapter(dishRepository, dishEntityMapper);
    }

    @Bean
    public ICustomerServicePort customerServicePort() {
        return new CustomerUseCase(dishPersistencePort(), restaurantPersistencePort);
    }
}