package com.reto.plazoleta.application.dto.request;

public class UpdateDishRequestDto {
    private Long idDish;
    private Double price;
    private String descriptionDish;
    private Long idRestaurant;

    public Long getIdDish() {
        return idDish;
    }

    public void setIdDish(Long idDish) {
        this.idDish = idDish;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}

