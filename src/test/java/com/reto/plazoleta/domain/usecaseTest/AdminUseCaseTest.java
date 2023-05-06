package com.reto.plazoleta.domain.usecaseTest;

import com.reto.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.reto.plazoleta.domain.usecase.AdminUseCase;
import com.reto.plazoleta.domain.model.RestaurantModelTest;
import com.reto.plazoleta.domain.exception.EmptyFieldsException;
import com.reto.plazoleta.domain.exception.InvalidDataException;
import com.reto.plazoleta.domain.model.RestaurantModel;
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
        when(restaurantPersistencePort.saveRestaurant(any())).thenReturn(RestaurantModelTest.restaurantModel());
        adminUseCase.saveRestaurant(RestaurantModelTest.restaurantModel());
        verify(restaurantPersistencePort).saveRestaurant(any(RestaurantModel.class));
    }

    @Test
    void throwEmptyFieldsExceptionWhenSavingRestaurant() {
        RestaurantModel restaurantModelWithEmptyFields = RestaurantModelTest.restaurantModelEmptyFields();
        Assertions.assertThrows(
                EmptyFieldsException.class,
                () -> { adminUseCase.saveRestaurant(restaurantModelWithEmptyFields); }
        );
    }

    @Test
    void throwInvalidDataExceptionWhenSavingRestaurantWithPhoneWrong() {
        RestaurantModel restaurantModelWithPhoneWrong = RestaurantModelTest.restaurantModelWrongPhone();
        Assertions.assertThrows(
                InvalidDataException.class,
                () -> { adminUseCase.saveRestaurant(restaurantModelWithPhoneWrong); }
        );
    }

    @Test
    void throwInvalidDataExceptionWhenSavingRestaurantWhereNameOnlyContainsNumbers() {
        RestaurantModel restaurantModelWhereNameOnlyContainsNumbers =
                RestaurantModelTest.restaurantModelWhereNameIsJustNumbers();
        Assertions.assertThrows(
                InvalidDataException.class,
                () -> { adminUseCase.saveRestaurant(restaurantModelWhereNameOnlyContainsNumbers); }
        );
    }
}