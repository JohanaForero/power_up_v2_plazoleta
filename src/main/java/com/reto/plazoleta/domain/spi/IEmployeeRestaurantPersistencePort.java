package com.reto.plazoleta.domain.spi;

import com.reto.plazoleta.domain.model.EmployeeRestaurantModel;

import java.util.List;

public interface IEmployeeRestaurantPersistencePort {

    EmployeeRestaurantModel saveEmployeeRestaurant(EmployeeRestaurantModel employeeRestaurantModel);
    List<EmployeeRestaurantModel> getAllemployeesRestaurants();
}
