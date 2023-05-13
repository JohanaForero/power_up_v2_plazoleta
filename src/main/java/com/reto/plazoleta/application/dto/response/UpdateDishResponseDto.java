package com.reto.plazoleta.application.dto.response;

public class UpdateDishResponseDto {
    private Double price;
    private String descriptionDish;
    private String nameCategory;
    private String nameRestaurant;

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public String getNameRestaurant() {
        return nameRestaurant;
    }

    public void setNameRestaurant(String nameRestaurant) {
        this.nameRestaurant = nameRestaurant;
    }

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
