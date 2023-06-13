package com.reto.plazoleta.infraestructure.configuration;


import com.reto.plazoleta.domain.spi.IDishPersistencePort;
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

    @Bean
    public IDishPersistencePort dishPersistencePort() {
        return new DishJpaAdapter(dishRepository, dishEntityMapper);
    }

}