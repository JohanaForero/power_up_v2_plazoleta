package com.reto.plazoleta.domain.model;

public class DishModel {

    private Long idDish;
    private String name;
    private String descriptionDish;
    private Double price;
    private String imageDish;
    private Boolean stateDish;
    private RestaurantModel restaurantModel;
    private CategoryModel categoryModel;

    public DishModel() {
    }

    public DishModel(Long idDish, String name, String descriptionDish, Double price, String imageDish, Boolean stateDish, RestaurantModel restaurantModel, CategoryModel categoryModel) {
        this.idDish = idDish;
        this.name = name;
        this.descriptionDish = descriptionDish;
        this.price = price;
        this.imageDish = imageDish;
        this.stateDish = stateDish;
        this.restaurantModel = restaurantModel;
        this.categoryModel = categoryModel;
    }

    public Long getIdDish() {
        return idDish;
    }

    public void setIdDish(Long idDish) {
        this.idDish = idDish;
    }

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

    public Boolean getStateDish() {
        return stateDish;
    }

    public void setStateDish(Boolean stateDish) {
        this.stateDish = stateDish;
    }

    public RestaurantModel getRestaurantModel() {
        return restaurantModel;
    }

    public void setRestaurantModel(RestaurantModel restaurantModel) {
        this.restaurantModel = restaurantModel;
    }

    public CategoryModel getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(CategoryModel categoryModel) {
        this.categoryModel = categoryModel;
    }
}
