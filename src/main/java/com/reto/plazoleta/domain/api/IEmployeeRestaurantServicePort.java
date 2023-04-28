package com.reto.plazoleta.domain.api;

import com.reto.plazoleta.domain.model.EmployeeRestaurantModel;

import java.util.List;

public interface IEmployeeRestaurantServicePort {
    List<EmployeeRestaurantModel> getAllEmployeesRestaurants();
    void saveEmployeeRestaurant(EmployeeRestaurantModel employeeRestaurantModel);
}
