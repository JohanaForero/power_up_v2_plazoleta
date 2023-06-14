package com.reto.plazoleta.domain.spi;

import com.reto.plazoleta.domain.model.CategoryModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICategoryPersistencePort {

    CategoryModel findById(Long idCategory);

    Page<CategoryModel> findAllCategories(Pageable pageable);
}