package com.reto.plazoleta.domain.model;

public class EmployeeRestaurantModel {
    private Long idEmployeeRestaurant;
    private RestaurantModel idRestaurant;

    public EmployeeRestaurantModel() {
    }

    public EmployeeRestaurantModel(Long idEmployeeRestaurant, RestaurantModel idRestaurant) {
        this.idEmployeeRestaurant = idEmployeeRestaurant;
        this.idRestaurant = idRestaurant;
    }

    public Long getIdEmployeeRestaurant() {
        return idEmployeeRestaurant;
    }

    public void setIdEmployeeRestaurant(Long idEmployeeRestaurant) {
        this.idEmployeeRestaurant = idEmployeeRestaurant;
    }

    public RestaurantModel getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(RestaurantModel idRestaurant) {
        this.idRestaurant = idRestaurant;
    }
}
