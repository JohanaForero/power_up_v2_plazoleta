package com.reto.plazoleta.infraestructure.drivenadapter.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "pedidos_platos")
public class OrderDishEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido_plato")
    private Long idOrderDish;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido", referencedColumnName = "id_pedido")
    private OrderEntity orderEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_plato", referencedColumnName = "idDish")
    private DishEntity dishEntity;

    @Column(name = "cantidad")
    private Integer amount;
    @Column(name = "gramos")
    private Integer grams;
    @Column(name = "acompañante_plato")
    private String accompaniment;
    @Column(name = "sabor_postre")
    private String flavor;
}