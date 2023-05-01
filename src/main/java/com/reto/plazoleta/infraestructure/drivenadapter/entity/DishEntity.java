package com.reto.plazoleta.infraestructure.drivenadapter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor // para que funcione las validaciones
@NoArgsConstructor
@Entity
@Table(name = "dish")
@Data
public class DishEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDish;
    @Column
    private String name;
    @Column
    private String descriptionDish;
    @Column
    private Double price;
    @Column
    private String imageDish;
    @Column
    private String stateDish;
    @Column
    private Long restaurantId;

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

    public void setImageDish(String imageDish) {
        this.imageDish = imageDish;
    }

    public String getStateDish() {
        return stateDish;
    }

    public void setStateDish(String stateDish) {
        this.stateDish = stateDish;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public RestaurantEntity getRestaurantEntity() {
        return restaurantEntity;
    }

    public void setRestaurantEntity(RestaurantEntity restaurantEntity) {
        this.restaurantEntity = restaurantEntity;
    }

    public CategoryEntity getCategoryEntity() {
        return categoryEntity;
    }

    public void setCategoryEntity(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Restaurant", referencedColumnName = "idRestaurant")
    private RestaurantEntity restaurantEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Category", referencedColumnName = "idCategory")
    private CategoryEntity categoryEntity;
}
