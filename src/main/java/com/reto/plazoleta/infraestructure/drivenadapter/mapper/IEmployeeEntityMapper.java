package com.reto.plazoleta.infraestructure.drivenadapter.mapper;

import com.reto.plazoleta.domain.model.EmployeeRestaurantModel;
import com.reto.plazoleta.infraestructure.drivenadapter.entity.EmployeeRestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface IEmployeeEntityMapper {

    @Mapping(target = "idEmployee", source ="idUserEmployee")
    EmployeeRestaurantEntity toEmployeeRestaurantEntity(EmployeeRestaurantModel employeeRestaurantModel);

    @Mapping(target = "idUserEmployee", source ="idEmployee")
    EmployeeRestaurantModel toEmployeeRestaurantModel(EmployeeRestaurantEntity employeeRestaurantEntity);
}
