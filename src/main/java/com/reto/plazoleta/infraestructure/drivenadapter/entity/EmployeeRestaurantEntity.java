package com.reto.plazoleta.infraestructure.drivenadapter.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "EmployeeRestaurant")
public class EmployeeRestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRestaurantEmployee;
    @Column(name = "id_restaurante")
    private Long idRestaurant;
    @Column(name = "id_usuario")
    private Long idEmployee;
}
