package com.reto.plazoleta.infraestructure.drivenadapter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "restaurant")
@Data
public class RestaurantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRestaurant;
    @Column
    private String name;
    @Column
    private String address;
    @Column
    private String phone;
    @Column
    private String urlLogo;
    @Column
    private Long  nit;
    @Column
    private Long idOwner;
}