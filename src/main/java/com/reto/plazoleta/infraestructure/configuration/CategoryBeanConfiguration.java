package com.reto.plazoleta.infraestructure.configuration;

import com.reto.plazoleta.domain.spi.ICategoryPersistencePort;
import com.reto.plazoleta.infraestructure.drivenadapter.mapper.ICategoryEntityMapper;
import com.reto.plazoleta.infraestructure.drivenadapter.persistence.CategoryJpaAdapter;
import com.reto.plazoleta.infraestructure.drivenadapter.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CategoryBeanConfiguration {

    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;

    @Bean
    public ICategoryPersistencePort categoryPersistencePort() {
        return new CategoryJpaAdapter(categoryRepository, categoryEntityMapper );
    }
}