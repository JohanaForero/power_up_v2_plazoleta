package com.reto.plazoleta.infraestructure.drivenadapter.mapper.pagemapper.implementation;

import com.reto.plazoleta.domain.model.DishModel;
import com.reto.plazoleta.infraestructure.drivenadapter.entity.DishEntity;
import com.reto.plazoleta.infraestructure.drivenadapter.mapper.pagemapper.IDishPageEntityMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.function.Function;
@Component
public class DishPageEntityMapperImpl implements IDishPageEntityMapper {
    @Override
    public Page<DishModel> toDishModelPage(Page<DishEntity> dishsEntityPage) {
        return dishsEntityPage.map(new Function<DishEntity, DishModel>() {
            @Override
            public DishModel apply(DishEntity dishEntity) {
                DishModel dishModel = new DishModel();
                return dishModel;
            }
        });
    }
}
