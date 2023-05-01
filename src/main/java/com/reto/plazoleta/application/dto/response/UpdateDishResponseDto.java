package com.reto.plazoleta.application.dto.response;

public class UpdateDishResponseDto {
    private Double price;
    private String descriptionDish;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescriptionDish() {
        return descriptionDish;
    }

    public void setDescriptionDish(String descriptionDish) {
        this.descriptionDish = descriptionDish;
    }
}
