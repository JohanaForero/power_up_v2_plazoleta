package com.reto.plazoleta.infraestructure.drivenadapter.repository;


import com.reto.plazoleta.infraestructure.drivenadapter.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<CategoryEntity,Long> {

}
