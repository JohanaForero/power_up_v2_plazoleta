package com.reto.plazoleta.infraestructure.drivenadapter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Restaurant", referencedColumnName = "idRestaurant")
    private RestaurantEntity restaurantEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Category", referencedColumnName = "idCategory")
    private CategoryEntity categoryEntity;
}
