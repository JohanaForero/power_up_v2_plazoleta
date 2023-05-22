package com.reto.plazoleta.domain.usecase;

import com.reto.plazoleta.domain.exception.EmptyFieldsException;
import com.reto.plazoleta.domain.exception.InvalidDataException;
import com.reto.plazoleta.domain.gateways.IUserGateway;
import com.reto.plazoleta.domain.model.RestaurantModel;
import com.reto.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.reto.plazoleta.domain.usecase.factory.FactoryRestaurantModelTest;
import com.reto.plazoleta.infraestructure.drivenadapter.gateways.User;
import com.reto.plazoleta.infraestructure.exception.NoDataFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantUseCaseTest {

    @InjectMocks
    private RestaurantUseCase restaurantUseCase;
    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;
    @Mock
    private IUserGateway userGateway;
    private final static String token = "";

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
    void test_findAllByOrderByNameAsc_withIntAsSizeItemsGreaterThanZero_ShouldReturnListRestaurantPageableWithAllFields() {
        //Given
        List<RestaurantModel> restaurantList = new ArrayList<>();
        restaurantList.add(new RestaurantModel("Restaurante 1", "Dirección 1", "3014896273", "http://restaurante1.com", 111111L, 1L));
        restaurantList.add(new RestaurantModel("Restaurante 2", "Dirección 2", "3224196283", "http://restaurante2.com", 222222L, 2L));
        Page<RestaurantModel> pageableRestaurantsExpected = new PageImpl<>(restaurantList);
        when(restaurantPersistencePort.findAllByOrderByNameAsc(PageRequest.of(0, 10))).thenReturn(pageableRestaurantsExpected);
        //When
        Page<RestaurantModel> result = restaurantUseCase.findAllByOrderByNameAsc(0, 10);
        //Then
        verify(restaurantPersistencePort, times(1)).findAllByOrderByNameAsc(PageRequest.of(0, 10));
        assertEquals(pageableRestaurantsExpected, result);
        assertEquals(pageableRestaurantsExpected.getTotalElements(), result.getTotalElements());
        assertEquals(pageableRestaurantsExpected.toList().get(0).getPhone(), result.toList().get(0).getPhone());
        assertEquals(pageableRestaurantsExpected.toList().get(1).getNit(), result.toList().get(1).getNit());
    }

    @Test
    void test_findAllByOrderByNameAsc_withIntAsSizeItemsGreaterThanZeroAndNoDataFound_ShouldThrowNoDataFoundException() {
        //Given
        int numberPage = 0;
        int sizeItems = 10;
        Page<RestaurantModel> emptyRestaurantPage = Page.empty();
        when(restaurantPersistencePort.findAllByOrderByNameAsc(PageRequest.of(numberPage, sizeItems))).thenReturn(emptyRestaurantPage);
        // When & Then
        Assertions.assertThrows(
                NoDataFoundException.class,
                () -> restaurantUseCase.findAllByOrderByNameAsc(numberPage, sizeItems)
        );
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