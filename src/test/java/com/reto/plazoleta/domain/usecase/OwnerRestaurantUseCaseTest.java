package com.reto.plazoleta.domain.usecase;

import com.reto.plazoleta.domain.model.DishModel;
import com.reto.plazoleta.domain.spi.IDishPersistencePort;
import com.reto.plazoleta.domain.usecase.factory.FactoryDishModelTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class OwnerRestaurantUseCaseTest {

    @InjectMocks
    OwnerRestaurantUseCase ownerRestaurantUseCase;

    @Mock
    IDishPersistencePort dishPersistencePort;

    @Test
    void mustSaveDish() {
        when(dishPersistencePort.saveDish(any())).thenReturn(FactoryDishModelTest.dishModel());
        ownerRestaurantUseCase.saveDish(FactoryDishModelTest.dishModel(), 1L, 1L);
        verify(dishPersistencePort).saveDish(any(DishModel.class));
    }
}
