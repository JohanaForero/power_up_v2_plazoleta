package com.reto.plazoleta.domain.api;

import com.reto.plazoleta.domain.model.CategoryModel;

import java.util.List;

public interface ICategoryServicePort {
    void saveCategory(CategoryModel categoryModel);
    List<CategoryModel> getAllCategorys();
}