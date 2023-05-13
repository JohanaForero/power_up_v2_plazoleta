package com.reto.plazoleta.application.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDishResponseDto {
    private String name;
    private String descriptionDish;
    private Double price;
    private String imageDish;
    private String stateDish;
    private Long idRestaurant;
    private String restaurant;
    private Long idCategory;
    private String category;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptionDish() {
        return descriptionDish;
    }

    public void setDescriptionDish(String descriptionDish) {
        this.descriptionDish = descriptionDish;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImageDish() {
        return imageDish;
    }

    public void setImageDish(String imageDish) {
        this.imageDish = imageDish;
    }

    public String getStateDish() {
        return stateDish;
    }

    public void setStateDish(String stateDish) {
        this.stateDish = stateDish;
    }

    public Long getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(Long idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
