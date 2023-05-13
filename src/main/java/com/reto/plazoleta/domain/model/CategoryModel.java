package com.reto.plazoleta.domain.model;

public class CategoryModel {

    private Long idCategory;
    private String nameCategory;
    private String description;

    public CategoryModel(){}

    public CategoryModel(Long idCategory, String nameCategory, String description) {
        this.idCategory = idCategory;
        this.nameCategory = nameCategory;
        this.description = description;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

}
