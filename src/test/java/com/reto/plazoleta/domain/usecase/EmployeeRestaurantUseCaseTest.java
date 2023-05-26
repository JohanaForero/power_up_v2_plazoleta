package com.reto.plazoleta.domain.usecase;

import com.reto.plazoleta.domain.model.EmployeeRestaurantModel;
import com.reto.plazoleta.domain.model.RestaurantModel;
import com.reto.plazoleta.domain.spi.IEmployeeRestaurantPersistencePort;
import com.reto.plazoleta.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeRestaurantUseCaseTest {

    @InjectMocks
    private EmployeeRestaurantUseCase employeeRestaurantUseCase;

    @Mock
    private IEmployeeRestaurantPersistencePort employeeRestaurantPersistencePort;

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @Test
    void test_saveEmployeeRestaurant_withValidFieldIdUserEmployeeInTheObjectAsEmployeeRestaurantModelAndLongAsIdOwnerHasARestaurant_shouldReturnAEmployeeRestaurantSavedModel() {
        //Given
        Long idOwner = 15L;
        RestaurantModel restaurantFoundExpected = new RestaurantModel(1L, "Puro sabor mexicano", "Libertadores Av, 18 st - 60",
                                    "+573112421021", "http://puro.sabot.com/mexicano.jpeg", 1234324454L, idOwner );

        EmployeeRestaurantModel employeeRestaurantRequestModel = new EmployeeRestaurantModel();
        employeeRestaurantRequestModel.setIdUserEmployee(1L);
        EmployeeRestaurantModel employeeRestaurantExpected = new EmployeeRestaurantModel(1L, 1L, restaurantFoundExpected);
        when(this.restaurantPersistencePort.findRestaurantByIdOwner(idOwner)).thenReturn(restaurantFoundExpected);
        when(this.employeeRestaurantPersistencePort.saveEmployeeRestaurant(employeeRestaurantRequestModel)).thenReturn(employeeRestaurantExpected);
        //When
        final EmployeeRestaurantModel employeeRestaurantSaved = this.employeeRestaurantUseCase.saveEmployeeRestaurant(employeeRestaurantRequestModel, idOwner);
        //Then
        verify(this.restaurantPersistencePort).findRestaurantByIdOwner(idOwner);
        verify(this.employeeRestaurantPersistencePort).saveEmployeeRestaurant(employeeRestaurantRequestModel);
        assertEquals(employeeRestaurantExpected.getIdRestaurantEmployee(), employeeRestaurantSaved.getIdRestaurantEmployee());
        assertEquals(employeeRestaurantExpected.getIdUserEmployee(), employeeRestaurantSaved.getIdUserEmployee());
        assertEquals(employeeRestaurantExpected.getRestaurantModel(), employeeRestaurantSaved.getRestaurantModel());
        assertEquals(employeeRestaurantExpected.getRestaurantModel().getIdRestaurant(), employeeRestaurantSaved.getRestaurantModel().getIdRestaurant());
        assertEquals(employeeRestaurantExpected.getRestaurantModel().getName(), employeeRestaurantSaved.getRestaurantModel().getName());
        assertEquals(employeeRestaurantExpected.getRestaurantModel().getAddress(), employeeRestaurantSaved.getRestaurantModel().getAddress());
        assertEquals(employeeRestaurantExpected.getRestaurantModel().getPhone(), employeeRestaurantSaved.getRestaurantModel().getPhone());
        assertEquals(employeeRestaurantExpected.getRestaurantModel().getUrlLogo(), employeeRestaurantSaved.getRestaurantModel().getUrlLogo());
        assertEquals(employeeRestaurantExpected.getRestaurantModel().getNit(), employeeRestaurantSaved.getRestaurantModel().getNit());
        assertEquals(employeeRestaurantExpected.getRestaurantModel().getIdOwner(), employeeRestaurantSaved.getRestaurantModel().getIdOwner());
    }

    @Test
    void test_saveEmployeeRestaurant_withIdOwnerFieldNoRestaurantIsFound_shouldThrow() {
    }
}