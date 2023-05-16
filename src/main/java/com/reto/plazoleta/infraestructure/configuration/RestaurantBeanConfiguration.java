package com.reto.plazoleta.infraestructure.configuration;

import com.reto.plazoleta.domain.api.IRestaurantServicePort;
import com.reto.plazoleta.domain.gateways.IUserGateway;
import com.reto.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.reto.plazoleta.domain.usecase.RestaurantUseCase;
import com.reto.plazoleta.infraestructure.drivenadapter.gateways.UserGatewayImpl;
import com.reto.plazoleta.infraestructure.drivenadapter.mapper.IRestaurantEntityMapper;
import com.reto.plazoleta.infraestructure.drivenadapter.mapper.pagemapper.IRestaurantPageEntityMapper;
import com.reto.plazoleta.infraestructure.drivenadapter.persistence.RestaurantJpaAdapter;
import com.reto.plazoleta.infraestructure.drivenadapter.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class RestaurantBeanConfiguration {

    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;
    private final IRestaurantPageEntityMapper restaurantPageEntityMapper;

    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort() {
        return new RestaurantJpaAdapter(restaurantRepository, restaurantEntityMapper, restaurantPageEntityMapper);
    }

    @Bean
    public IUserGateway userGateway() {
        return new UserGatewayImpl();
    }

    @Bean
    public IRestaurantServicePort restaurantUseCase() {
        return new RestaurantUseCase(restaurantPersistencePort(), userGateway());
    }

}
