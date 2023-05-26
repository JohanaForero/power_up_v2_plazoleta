package com.reto.plazoleta.domain.usecase;

import com.reto.plazoleta.domain.api.IEmployeeRestaurantServicePort;
import com.reto.plazoleta.domain.model.EmployeeRestaurantModel;
import com.reto.plazoleta.domain.model.RestaurantModel;
import com.reto.plazoleta.domain.spi.IEmployeeRestaurantPersistencePort;
import com.reto.plazoleta.domain.spi.IRestaurantPersistencePort;

public class EmployeeRestaurantUseCase implements IEmployeeRestaurantServicePort {

    private final IEmployeeRestaurantPersistencePort employeeRestaurantPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;

    public EmployeeRestaurantUseCase(IEmployeeRestaurantPersistencePort employeeRestaurantPersistencePort, IRestaurantPersistencePort restaurantPersistencePort) {
        this.employeeRestaurantPersistencePort = employeeRestaurantPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
    }

    @Override
    public EmployeeRestaurantModel saveEmployeeRestaurant(EmployeeRestaurantModel employeeRestaurantModel, Long idOwner) {
        final RestaurantModel restaurantModel = this.restaurantPersistencePort.findRestaurantByIdOwner(idOwner);
        if(restaurantModel == null) {
            throw new RuntimeException("Restaurant not Exist");
        }
        employeeRestaurantModel.setRestaurantModel(restaurantModel);
        final EmployeeRestaurantModel employeeSavedModel = this.employeeRestaurantPersistencePort.saveEmployeeRestaurant(employeeRestaurantModel);
        return employeeSavedModel;
    }
}
