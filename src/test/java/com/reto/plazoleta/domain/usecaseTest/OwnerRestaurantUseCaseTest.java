package com.reto.plazoleta.domain.usecaseTest;

import com.reto.plazoleta.domain.model.DishModel;
import com.reto.plazoleta.domain.model.DishModelTest;
import com.reto.plazoleta.domain.spi.IDishPersistencePort;

import com.reto.plazoleta.domain.usecase.OwnerRestaurantUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OwnerRestaurantUseCaseTest {

    @Mock
    private IDishPersistencePort dishPersistencePort;

    private OwnerRestaurantUseCase ownerRestaurantUseCase;

    @BeforeEach
    public void setUp(){
        ownerRestaurantUseCase = new OwnerRestaurantUseCase(dishPersistencePort);
    }
    @Test
    public void test_updateDish_dishModelFieldsPriceAndDescriptionAndIdDish_whenSystemUpdatePriceAndDescriptionFromDish_shouldReturnVoid() {DishModel dishModelActual = new DishModel();
        DishModel dishModelCurrent = new DishModel();
        dishModelCurrent.setIdDish(1L);
        dishModelCurrent.setPrice(10.000);
        dishModelCurrent.setDescriptionDish("Arroz con Camarones");
        when(dishPersistencePort.findById(1L)).thenReturn(DishModelTest.dishModel());
        when(dishPersistencePort.updateDish(any())).thenReturn(DishModelTest.dishModelExpected());
        ownerRestaurantUseCase.updateDish(dishModelCurrent);
        ArgumentCaptor<DishModel> captor = ArgumentCaptor.forClass(DishModel.class);
        verify(dishPersistencePort).updateDish(captor.capture());
        DishModel dishModelUpdate = captor.getValue();
        assertEquals(DishModelTest.dishModelExpected().getPrice(), dishModelUpdate.getPrice());
        assertEquals(DishModelTest.dishModelExpected().getDescriptionDish(), dishModelUpdate.getDescriptionDish());
    }
    @Test
    public void test_updateDishState_withValidState_whenSystemHasExistingDish_ShouldUpdateStateSuccessfully(){}

    @Test
    public void test_updateDishExistingDishModel_withValidValues_ShouldUpdatePriceStateAndDescription(){
        double newPrice = 9.99;
        boolean newState = true;
        String newDescription = "Plato actualizado";

    }
}
