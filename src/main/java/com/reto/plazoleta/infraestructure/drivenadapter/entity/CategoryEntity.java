package com.reto.plazoleta.infraestructure.drivenadapter.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor // para que funcione las validaciones
@NoArgsConstructor
@Entity
@Table(name = "category")
@Getter
@Setter
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategory;
    @Column
    private String name;
    @Column
    private String description;
}
