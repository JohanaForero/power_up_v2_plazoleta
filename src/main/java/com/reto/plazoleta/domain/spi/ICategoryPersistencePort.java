package com.reto.plazoleta.domain.spi;

import com.reto.plazoleta.domain.model.CategoryModel;

import java.util.List;
public interface ICategoryPersistencePort {
    CategoryModel saveCategory(CategoryModel categoryModel);
    List<CategoryModel> getAllCategorys();
}