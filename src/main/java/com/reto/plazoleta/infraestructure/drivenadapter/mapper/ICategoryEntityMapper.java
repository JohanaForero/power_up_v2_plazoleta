package com.reto.plazoleta.infraestructure.drivenadapter.mapper;

import com.reto.plazoleta.domain.model.CategoryModel;
import com.reto.plazoleta.infraestructure.drivenadapter.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICategoryEntityMapper {

    CategoryModel toCategoryEntity(CategoryEntity categoryEntity);
    CategoryModel toModel(CategoryEntity categoryEntity);
}
