package com.reto.plazoleta.domain.usecase;

import com.reto.plazoleta.domain.exception.InvalidDataException;
import com.reto.plazoleta.domain.gateways.IUserGateway;
import com.reto.plazoleta.domain.model.RestaurantModel;
import com.reto.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.reto.plazoleta.infraestructure.drivenadapter.gateways.User;
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
import static org.mockito.Mockito.*;

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
    void test_SaveRestaurant_withNonEmptyRestaurantModelAndValidToken_withNameAndPhoneFormatCorrectAndIdOwnerExistWithRoleOwner_ShouldReturnVoid() {
        User userWithRoleOwner = new User();
        userWithRoleOwner.setRol("PROPIETARIO");

        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setIdRestaurant(1L);
        restaurantModel.setName("Sabroson17");
        restaurantModel.setUrlLogo("http://sabroson.img");
        restaurantModel.setAddress("Cra 10");
        restaurantModel.setPhone("3018452367");
        restaurantModel.setNit(843775L);
        restaurantModel.setIdOwner(5L);

        when(userGateway.getUserById(5L, token)).thenReturn(userWithRoleOwner);
        when(restaurantPersistencePort.saveRestaurant(restaurantModel)).thenReturn(restaurantModel);

        restaurantUseCase.saveRestaurant(restaurantModel, token);

        verify(restaurantPersistencePort).saveRestaurant(restaurantModel);
    }

    @Test
    void test_findAllByOrderByNameAsc_withIntAsSizeItemsGreaterThanZero_ShouldReturnListRestaurantPageableWithAllFields() {
        List<RestaurantModel> restaurantList = new ArrayList<>();
        restaurantList.add(new RestaurantModel("Restaurante 1", "Dirección 1", "3014896273", "http://restaurante1.com", 111111L, 1L));
        restaurantList.add(new RestaurantModel("Restaurante 2", "Dirección 2", "3224196283", "http://restaurante2.com", 222222L, 2L));
        Page<RestaurantModel> pageableRestaurantsExpected = new PageImpl<>(restaurantList);
        when(restaurantPersistencePort.findAllByOrderByNameAsc(PageRequest.of(0, 10))).thenReturn(pageableRestaurantsExpected);

        Page<RestaurantModel> result = restaurantUseCase.findAllByOrderByNameAsc(0, 10);

        verify(restaurantPersistencePort, times(1)).findAllByOrderByNameAsc(PageRequest.of(0, 10));
        assertEquals(pageableRestaurantsExpected, result);
        assertEquals(pageableRestaurantsExpected.getTotalElements(), result.getTotalElements());
        assertEquals(pageableRestaurantsExpected.toList().get(0).getPhone(), result.toList().get(0).getPhone());
        assertEquals(pageableRestaurantsExpected.toList().get(1).getNit(), result.toList().get(1).getNit());
    }

    @Test
    void test_validateRestaurantPhone_withStringAsPhoneInvalidRestaurant_ShouldThrowInvalidDataException() {
        RestaurantModel restaurantWithPhoneWrong = new RestaurantModel();
        restaurantWithPhoneWrong.setName("Sabroson17");
        restaurantWithPhoneWrong.setUrlLogo("http://sabroson.img");
        restaurantWithPhoneWrong.setAddress("Cra 10");
        restaurantWithPhoneWrong.setPhone("4563018452367");
        restaurantWithPhoneWrong.setNit(843775L);
        restaurantWithPhoneWrong.setIdOwner(5L);

        Assertions.assertThrows(
                InvalidDataException.class,
                () -> restaurantUseCase.saveRestaurant(restaurantWithPhoneWrong, token)
        );
    }

    @Test
    void test_isContainsRestaurantNameOnlyNumbers_withStringAsNameRestaurantWithOnlyNumbers_ShouldThrowInvalidDataException() {
        RestaurantModel restaurantWhereNameOnlyContainsNumbers = new RestaurantModel();
        restaurantWhereNameOnlyContainsNumbers.setName("17645676");
        restaurantWhereNameOnlyContainsNumbers.setUrlLogo("http://sabroson.img");
        restaurantWhereNameOnlyContainsNumbers.setAddress("Cra 10");
        restaurantWhereNameOnlyContainsNumbers.setPhone("3018452367");
        restaurantWhereNameOnlyContainsNumbers.setNit(843775L);
        restaurantWhereNameOnlyContainsNumbers.setIdOwner(5L);

        Assertions.assertThrows(
                InvalidDataException.class,
                () -> restaurantUseCase.saveRestaurant(restaurantWhereNameOnlyContainsNumbers, token)
        );
    }
}