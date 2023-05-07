package com.reto.plazoleta.domain.usecase;

import com.reto.plazoleta.domain.FactoryDishModelTest;
import com.reto.plazoleta.domain.spi.IDishPersistencePort;
import com.reto.plazoleta.domain.model.DishModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class OwnerRestaurantUseCaseTest {

    @InjectMocks
    private OwnerRestaurantUseCase ownerRestaurantUseCase;

    @Mock
    private IDishPersistencePort dishPersistencePort;

    @Test
    void mustSaveDish() {
        when(dishPersistencePort.saveDish(any())).thenReturn(FactoryDishModelTest.dishModel());
        ownerRestaurantUseCase.saveDish(FactoryDishModelTest.dishModel());
        verify(dishPersistencePort).saveDish(any(DishModel.class));
    }

    @Test
    public void test_updateDish_dishModelFieldsPriceAndDescriptionAndIdDish_whenSystemUpdatePriceAndDescriptionFromDish_shouldReturnVoid() {DishModel dishModelActual = new DishModel();
        DishModel dishModelCurrent = new DishModel();
        dishModelCurrent.setIdDish(1L);
        dishModelCurrent.setPrice(10.000);
        dishModelCurrent.setDescriptionDish("Arroz con Camarones");
        when(dishPersistencePort.findById(1L)).thenReturn(FactoryDishModelTest.dishModel());
        when(dishPersistencePort.updateDish(any())).thenReturn(FactoryDishModelTest.dishModelExpected());
        ownerRestaurantUseCase.updateDish(dishModelCurrent);
        ArgumentCaptor<DishModel> captor = ArgumentCaptor.forClass(DishModel.class);
        verify(dishPersistencePort).updateDish(captor.capture());
        DishModel dishModelUpdate = captor.getValue();
        assertEquals(FactoryDishModelTest.dishModelExpected().getPrice(), dishModelUpdate.getPrice());
        assertEquals(FactoryDishModelTest.dishModelExpected().getDescriptionDish(), dishModelUpdate.getDescriptionDish());
    }
}
