package com.reto.plazoleta.infraestructure.drivenadapter.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "pedidos")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Long idOrder;
    @Column(name = "id_cliente")
    private Long idUserCustomer;
    @Column(name = "fecha")
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private StatusOrder status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_chef", referencedColumnName = "idRestaurantEmployee")
    private EmployeeRestaurantEntity employeeRestaurantEntity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_restaurante", referencedColumnName = "idRestaurant")
    private RestaurantEntity restaurantEntity;

    @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDishEntity> ordersDishesEntity;
}
