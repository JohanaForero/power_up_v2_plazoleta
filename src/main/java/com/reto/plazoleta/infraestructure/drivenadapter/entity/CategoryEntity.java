package com.reto.plazoleta.infraestructure.drivenadapter.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "category")
@Getter
@Setter
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategory;
    @Column(name = "nombre")
    @Enumerated(EnumType.STRING)
    private TypeDish name;
    @Column
    private String description;
}
