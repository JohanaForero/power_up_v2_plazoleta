package com.reto.plazoleta.infraestructure.drivenadapter.mapper.pagemapper;

import com.reto.plazoleta.domain.model.DishModel;
import com.reto.plazoleta.infraestructure.drivenadapter.entity.DishEntity;
import org.springframework.data.domain.Page;

public interface IDishPageEntityMapper {
    Page<DishModel> toDishModelPage(Page<DishEntity> dishEntity);

}
