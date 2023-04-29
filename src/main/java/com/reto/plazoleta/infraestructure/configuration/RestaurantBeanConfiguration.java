package com.reto.plazoleta.infraestructure.configuration;

import com.reto.plazoleta.domain.api.IAdminUseCasePort;
import com.reto.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.reto.plazoleta.domain.usecase.AdminUseCase;
import com.reto.plazoleta.infraestructure.drivenadapter.mapper.IRestaurantEntityMapper;
import com.reto.plazoleta.infraestructure.drivenadapter.persistence.RestaurantJpaAdapter;
import com.reto.plazoleta.infraestructure.drivenadapter.repository.IRestaurantRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestaurantBeanConfiguration {

    private final IRestaurantRepository restauranteRepository;
    private final IRestaurantEntityMapper restauranteEntityMapper;

    public RestaurantBeanConfiguration(IRestaurantRepository restauranteRepository, IRestaurantEntityMapper restauranteEntityMapper) {
        this.restauranteRepository = restauranteRepository;
        this.restauranteEntityMapper = restauranteEntityMapper;
    }

    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort() {
        return new RestaurantJpaAdapter(restauranteRepository, restauranteEntityMapper);
    }

    @Bean
    public IAdminUseCasePort adminUseCasePort() {
        return new AdminUseCase(restaurantPersistencePort());
    }

}
