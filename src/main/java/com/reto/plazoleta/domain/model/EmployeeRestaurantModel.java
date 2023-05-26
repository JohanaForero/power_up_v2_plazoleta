package com.reto.plazoleta.domain.model;

public class EmployeeRestaurantModel {

    private Long idRestaurantEmployee;
    private Long idUserEmployee;
    private RestaurantModel restaurantModel;

    public EmployeeRestaurantModel() {
    }

    public EmployeeRestaurantModel(Long idRestaurantEmployee, Long idUserEmployee, RestaurantModel restaurantModel) {
        this.idRestaurantEmployee = idRestaurantEmployee;
        this.idUserEmployee = idUserEmployee;
        this.restaurantModel = restaurantModel;
    }

    public Long getIdRestaurantEmployee() {
        return idRestaurantEmployee;
    }

    public void setIdRestaurantEmployee(Long idRestaurantEmployee) {
        this.idRestaurantEmployee = idRestaurantEmployee;
    }

    public Long getIdUserEmployee() {
        return idUserEmployee;
    }

    public void setIdUserEmployee(Long idUserEmployee) {
        this.idUserEmployee = idUserEmployee;
    }

    public RestaurantModel getRestaurantModel() {
        return restaurantModel;
    }

    public void setRestaurantModel(RestaurantModel restaurantModel) {
        this.restaurantModel = restaurantModel;
    }
}
