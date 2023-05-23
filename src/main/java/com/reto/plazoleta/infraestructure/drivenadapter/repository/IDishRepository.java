package com.reto.plazoleta.infraestructure.drivenadapter.repository;

import com.reto.plazoleta.infraestructure.drivenadapter.entity.DishEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IDishRepository  extends JpaRepository<DishEntity, Long> {

}