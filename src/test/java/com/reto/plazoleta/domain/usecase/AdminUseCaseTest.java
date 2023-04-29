package com.reto.plazoleta.domain.usecase;

import com.reto.plazoleta.domain.exception.EmptyFieldsException;
import com.reto.plazoleta.domain.exception.InvalidDataException;
import com.reto.plazoleta.domain.model.RestaurantModel;
import com.reto.plazoleta.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AdminUseCaseTest {

    @InjectMocks
    AdminUseCase adminUseCase;

    @Mock
    IRestaurantPersistencePort restaurantPersistencePort;

    @Test
    void mustSaveRestaurant() {
        when(restaurantPersistencePort.saveRestaurant(any())).thenReturn(FactoryRestaurantModelTest.restaurantModel());
        adminUseCase.saveRestaurant(FactoryRestaurantModelTest.restaurantModel());
        verify(restaurantPersistencePort).saveRestaurant(any(RestaurantModel.class));
    }

    @Test
    void throwEmptyFieldsExceptionWhenSavingRestaurant() {
        RestaurantModel restaurantModelWithEmptyFields = FactoryRestaurantModelTest.restaurantModelEmptyFields();
        Assertions.assertThrows(
                EmptyFieldsException.class,
                () -> { adminUseCase.saveRestaurant(restaurantModelWithEmptyFields); }
        );
    }

    @Test
    void throwInvalidDataExceptionWhenSavingRestaurantWithPhoneWrong() {
        RestaurantModel restaurantModelWithPhoneWrong = FactoryRestaurantModelTest.restaurantModelWrongPhone();
        Assertions.assertThrows(
                InvalidDataException.class,
                () -> { adminUseCase.saveRestaurant(restaurantModelWithPhoneWrong); }
        );
    }

    @Test
    void throwInvalidDataExceptionWhenSavingRestaurantWhereNameOnlyContainsNumbers() {
        RestaurantModel restaurantModelWhereNameOnlyContainsNumbers =
                FactoryRestaurantModelTest.restaurantModelWhereNameIsJustNumbers();
        Assertions.assertThrows(
                InvalidDataException.class,
                () -> { adminUseCase.saveRestaurant(restaurantModelWhereNameOnlyContainsNumbers); }
        );
    }
}