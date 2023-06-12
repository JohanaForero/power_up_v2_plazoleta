package com.reto.plazoleta.infraestructure.drivenadapter.mapper;

import com.reto.plazoleta.domain.model.EmployeeRestaurantModel;
import com.reto.plazoleta.infraestructure.drivenadapter.entity.EmployeeRestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface IEmployeeEntityMapper {

    EmployeeRestaurantEntity toEmployeeRestaurantEntity(EmployeeRestaurantModel employeeRestaurantModel);

    EmployeeRestaurantModel toEmployeeRestaurantModel(EmployeeRestaurantEntity employeeRestaurantEntity);
}
