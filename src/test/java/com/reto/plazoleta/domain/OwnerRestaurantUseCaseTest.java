package com.reto.plazoleta.domain;

import com.reto.plazoleta.domain.model.CategoryModel;
import com.reto.plazoleta.domain.model.DishModel;
import com.reto.plazoleta.domain.model.RestaurantModel;
import com.reto.plazoleta.domain.spi.ICategoryPersistencePort;
import com.reto.plazoleta.domain.spi.IDishPersistencePort;
import com.reto.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.reto.plazoleta.domain.usecase.OwnerRestaurantUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerRestaurantUseCaseTest {

    private OwnerRestaurantUseCase ownerRestaurantUseCase;

    @Mock
    private IDishPersistencePort dishPersistencePort;

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @BeforeEach
    void setUp() {
        ownerRestaurantUseCase = new OwnerRestaurantUseCase(dishPersistencePort, restaurantPersistencePort, categoryPersistencePort);
    }

    @Test
    public void test_saveDish_withRequestCompleteAndNameUnique_ShouldResponseCreateDishSuccess() {
        RestaurantModel restaurantModel = new RestaurantModel(1L, "salado", "bellavista", "+123456779", "urlLogo", 1L, 12344L);
        CategoryModel categoryModel = new CategoryModel(1L, "salados", "salado");
        DishModel dishModel = new DishModel(1L, "cuscu", "salsa", 12.00, "urlImagen", true, restaurantModel, categoryModel);

        when(restaurantPersistencePort.findByIdRestaurant(eq(1L))).thenReturn(restaurantModel);
        when(categoryPersistencePort.findById(eq(1L))).thenReturn(categoryModel);
        when(dishPersistencePort.saveDish(argThat(dish -> dish.getName().equals("cuscu")))).thenReturn(dishModel);

        DishModel savedDish = ownerRestaurantUseCase.saveDish(dishModel);

        assertNotNull(savedDish);
        assertEquals(restaurantModel, savedDish.getRestaurantModel());
        assertEquals(categoryModel, savedDish.getCategoryModel());
        assertTrue(savedDish.getStateDish());

        verify(restaurantPersistencePort, times(1)).findByIdRestaurant(eq(1L));
        verify(categoryPersistencePort, times(1)).findById(eq(1L));
        verify(dishPersistencePort, times(1)).saveDish(argThat(dish -> dish.getName().equals("cuscu")));
    }
}



