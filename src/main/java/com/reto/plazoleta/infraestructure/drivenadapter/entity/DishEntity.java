package com.reto.plazoleta.infraestructure.drivenadapter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor // para que funcione las validaciones
@NoArgsConstructor
@Entity
@Table(name = "dish")
@Data
public class DishEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotBlank
    private Long idDish;
    @Column
    @NotBlank
    private String name;
    @Column
    @NotBlank
    private String descriptionDish;
    @Column
    @NotBlank
    private Double price;
    @Column
    @NotBlank
    private String imageDish;
    @Column
    @NotBlank
    private String stateDish;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Restaurant", referencedColumnName = "idRestaurant")
    @NotNull
    private RestaurantEntity restaurantEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Category", referencedColumnName = "idCategory")
    @NotNull
    private CategoryEntity categoryEntity;
}
