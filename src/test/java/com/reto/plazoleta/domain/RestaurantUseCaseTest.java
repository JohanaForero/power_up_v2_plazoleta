package com.reto.plazoleta.domain;

import com.reto.plazoleta.domain.exception.EmptyFieldsException;
import com.reto.plazoleta.domain.exception.InvalidDataException;
import com.reto.plazoleta.domain.gateways.IUserGateway;
import com.reto.plazoleta.domain.model.RestaurantModel;
import com.reto.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.reto.plazoleta.domain.usecase.RestaurantUseCase;
import com.reto.plazoleta.factory.FactoryRestaurantModelTest;
import com.reto.plazoleta.infraestructure.drivenadapter.gateways.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RestaurantUseCaseTest {

    private RestaurantUseCase restaurantUseCase;

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private IUserGateway userGateway;
    private final static String token = "";

    @BeforeEach
    void setUp() {
        restaurantUseCase = new RestaurantUseCase(restaurantPersistencePort, userGateway);

    }

    @Test
    void mustSaveRestaurant() {
        User userTest = new User();
        userTest.setRol("PROPIETARIO");
        when(userGateway.getUserById(any(), any())).thenReturn(userTest);
        when(restaurantPersistencePort.saveRestaurant(any())).thenReturn(FactoryRestaurantModelTest.restaurantModel());
        restaurantUseCase.saveRestaurant(FactoryRestaurantModelTest.restaurantModel(), token);
        verify(restaurantPersistencePort).saveRestaurant(any(RestaurantModel.class));
    }

    @Test
    void throwEmptyFieldsExceptionWhenSavingRestaurant() {
        RestaurantModel restaurantModelWithEmptyFields = FactoryRestaurantModelTest.restaurantModelEmptyFields();
        Assertions.assertThrows(
                EmptyFieldsException.class,
                () -> { restaurantUseCase.saveRestaurant(restaurantModelWithEmptyFields, token); }
        );
    }

    @Test
    void throwInvalidDataExceptionWhenSavingRestaurantWithPhoneWrong() {
        RestaurantModel restaurantModelWithPhoneWrong = FactoryRestaurantModelTest.restaurantModelWrongPhone();
        Assertions.assertThrows(
                InvalidDataException.class,
                () -> { restaurantUseCase.saveRestaurant(restaurantModelWithPhoneWrong, token); }
        );
    }

    @Test
    void throwInvalidDataExceptionWhenSavingRestaurantWhereNameOnlyContainsNumbers() {
        RestaurantModel restaurantModelWhereNameOnlyContainsNumbers =
                FactoryRestaurantModelTest.restaurantModelWhereNameIsJustNumbers();
        Assertions.assertThrows(
                InvalidDataException.class,
                () -> { restaurantUseCase.saveRestaurant(restaurantModelWhereNameOnlyContainsNumbers, token); }
        );
    }

}