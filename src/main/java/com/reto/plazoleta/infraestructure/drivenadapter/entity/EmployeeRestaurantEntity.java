package com.reto.plazoleta.infraestructure.drivenadapter.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Getter
@Setter
@Table(name = "EmployeeRestaurant")
public class EmployeeRestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRestaurantEmployee;
    private Long idRestaurant;
    private Long idEmployee;
}
