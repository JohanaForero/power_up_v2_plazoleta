package com.reto.plazoleta.domain.model;

public class DishModel {

    private long idDish;
    private String name;
    private String descriptionDish;
    private Double price;
    private String imageDish;
    private boolean stateDish;
    public DishModel() {
    }

    public DishModel(String name, String descriptionDish, Double price, String imageDish, boolean stateDish) {
        this.name = name;
        this.descriptionDish = descriptionDish;
        this.price = price;
        this.imageDish = imageDish;
        this.stateDish = stateDish;
    }


    public Long getIdDish(long idDish) {
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
    public void setImageDish(String imageDish) {
        this.imageDish = imageDish;
    }
    public boolean getStateDish() {
        return stateDish;
    }
    public void setStateDish(boolean stateDish) {
        this.stateDish = stateDish;
    }
}
