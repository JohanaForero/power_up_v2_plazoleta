package com.reto.plazoleta.application.mapper.requestmapper;

import com.reto.plazoleta.application.dto.request.CreateCategoryRequestDto;
import com.reto.plazoleta.domain.model.CategoryModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICategoryRequestMapper {
    CategoryModel toCategoryModel(CreateCategoryRequestDto createResponseDto);
}
