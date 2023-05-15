package com.reto.plazoleta.domain;

import com.reto.plazoleta.domain.api.IEmployeeRestaurantServicePort;
import com.reto.plazoleta.domain.model.EmployeeRestaurantModel;
import com.reto.plazoleta.domain.spi.IEmployeeRestaurantPersistencePort;

import java.util.List;

public class EmployeeRestaurantUseCase implements IEmployeeRestaurantServicePort {

    private final IEmployeeRestaurantPersistencePort employeeRestaurantPersistencePort;

    public EmployeeRestaurantUseCase(IEmployeeRestaurantPersistencePort employeeRestaurantPersistencePort) {
        this.employeeRestaurantPersistencePort = employeeRestaurantPersistencePort;
    }

    @Override
    public List<EmployeeRestaurantModel> getAllEmployeesRestaurants() {
        return employeeRestaurantPersistencePort.getAllemployeesRestaurants();
    }

    @Override
    public void saveEmployeeRestaurant(EmployeeRestaurantModel employeeRestaurantModel) {
        employeeRestaurantPersistencePort.saveEmployeeRestaurant(employeeRestaurantModel);
    }

}
