package com.reto.plazoleta.domain.spi;

import com.reto.plazoleta.domain.OwnerRestaurantUseCase;
import com.reto.plazoleta.domain.api.IOwnerRestaurantServicePort;
import com.reto.plazoleta.domain.model.CategoryModel;
import com.reto.plazoleta.domain.model.DishModel;
import com.reto.plazoleta.domain.model.RestaurantModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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

    @Mock
    private CategoryModel categoryModel;

    @BeforeEach
    void setUp() {
        ownerRestaurantUseCase = new OwnerRestaurantUseCase(dishPersistencePort, restaurantPersistencePort, categoryPersistencePort);
    }

    @Test
    public void testSaveDish_When_DishModelIsValid_Then_ReturnsSavedDish() {

        RestaurantModel restaurantModel = new RestaurantModel(1L,"salado","bellavista","+123456779","urlLogo",1L,12344L);
        CategoryModel categoryModel = new CategoryModel(1L,"salados","salado");
        DishModel dishModel = new DishModel(1l,"cuscu","salsa",12.00,"urlImagen",true,restaurantModel,categoryModel);

        when(restaurantPersistencePort.findByIdRestaurant(anyLong())).thenReturn(restaurantModel);
        when(categoryPersistencePort.findById(anyLong())).thenReturn(categoryModel);
        when(dishPersistencePort.saveDish(any(DishModel.class))).thenReturn(dishModel);

        DishModel savedDish = ownerRestaurantUseCase.saveDish(dishModel);

        assertNotNull(savedDish);
        assertEquals(restaurantModel, savedDish.getRestaurantModel());
        assertEquals(categoryModel, savedDish.getCategoryModel());
        assertTrue(savedDish.getStateDish());

        verify(restaurantPersistencePort, times(1)).findByIdRestaurant(anyLong());
        verify(categoryPersistencePort, times(1)).findById(anyLong());
        verify(dishPersistencePort, times(1)).saveDish(any(DishModel.class));
    }
}



