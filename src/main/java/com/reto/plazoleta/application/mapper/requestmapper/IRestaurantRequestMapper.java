package com.reto.plazoleta.application.mapper.requestmapper;

import com.reto.plazoleta.application.dto.RequestToCreateRestaurantDto;
import com.reto.plazoleta.domain.model.RestaurantModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestaurantRequestMapper {

    RestaurantModel toRestaurantModel(RequestToCreateRestaurantDto requestToCreateRestaurantDto);
}
