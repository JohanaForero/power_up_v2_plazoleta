package com.reto.plazoleta.infraestructure.drivenadapter.persistence;


import com.reto.plazoleta.domain.model.CategoryModel;
import com.reto.plazoleta.domain.spi.ICategoryPersistencePort;
import com.reto.plazoleta.infraestructure.drivenadapter.entity.CategoryEntity;
import com.reto.plazoleta.infraestructure.drivenadapter.mapper.ICategoryEntityMapper;
import com.reto.plazoleta.infraestructure.drivenadapter.repository.ICategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class CategoryJpaAdapter implements ICategoryPersistencePort{

    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;

    public CategoryJpaAdapter(ICategoryRepository categoryRepository, ICategoryEntityMapper categoryEntityMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryEntityMapper = categoryEntityMapper;
    }

    @Override
    public CategoryModel saveCategory(CategoryModel categoryModel) {
        CategoryEntity categoryEntity = categoryEntityMapper.toCategoryEntity(categoryModel);
        return categoryEntityMapper.toCategoryModel(
                categoryRepository.save(categoryEntity));
    }

    @Override
    public CategoryModel findById(Long idCategory) {
        return categoryEntityMapper.toCategoryModel(categoryRepository.findById(idCategory).orElse(null));
    }


}
