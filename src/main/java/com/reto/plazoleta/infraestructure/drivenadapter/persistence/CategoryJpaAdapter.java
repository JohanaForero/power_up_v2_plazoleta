package com.reto.plazoleta.infraestructure.drivenadapter.persistence;


import com.reto.plazoleta.domain.model.CategoryModel;
import com.reto.plazoleta.domain.spi.ICategoryPersistencePort;
import com.reto.plazoleta.infraestructure.drivenadapter.mapper.ICategoryEntityMapper;
import com.reto.plazoleta.infraestructure.drivenadapter.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryJpaAdapter implements ICategoryPersistencePort {

    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;

    @Override
    public CategoryModel findById(Long idCategory) {
        return categoryEntityMapper.toCategoryEntity(categoryRepository.findById(idCategory).orElse(null));
    }
}
