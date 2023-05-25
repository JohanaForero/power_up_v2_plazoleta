package com.reto.plazoleta.domain.usecase;

import com.reto.plazoleta.domain.exception.DishNotExistsException;
import com.reto.plazoleta.domain.exception.InvalidDataException;
import com.reto.plazoleta.domain.model.CategoryModel;
import com.reto.plazoleta.domain.model.DishModel;
import com.reto.plazoleta.domain.model.RestaurantModel;
import com.reto.plazoleta.domain.spi.ICategoryPersistencePort;
import com.reto.plazoleta.domain.spi.IDishPersistencePort;
import com.reto.plazoleta.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerRestaurantUseCaseTest {

    @InjectMocks
    private OwnerRestaurantUseCase ownerRestaurantUseCase;

    @Mock
    private IDishPersistencePort dishPersistencePort;

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @Test
    void test_saveDish_withRequestCompleteAndNameUnique_ShouldResponseCreateDishSuccess() {
        //Given
        RestaurantModel restaurantModel = new RestaurantModel(1L, "salado", "bellavista", "+123456779", "urlLogo", 1L, 12344L);
        CategoryModel categoryModel = new CategoryModel(1L, "salados", "salado");
        DishModel dishModel = new DishModel(1L, "cuscu", "salsa", 12.00, "urlImagen", true, restaurantModel, categoryModel);

        when(restaurantPersistencePort.findByIdRestaurant(1L)).thenReturn(restaurantModel);
        when(categoryPersistencePort.findById(1L)).thenReturn(categoryModel);
        when(dishPersistencePort.saveDish(argThat(dish -> dish.getName().equals("cuscu")))).thenReturn(dishModel);

        //When
        DishModel savedDish = ownerRestaurantUseCase.saveDish(dishModel);

        //Then
        verify(restaurantPersistencePort, times(1)).findByIdRestaurant(1L);
        verify(categoryPersistencePort, times(1)).findById(1L);
        verify(dishPersistencePort, times(1)).saveDish(argThat(dish -> dish.getName().equals("cuscu")));

        assertNotNull(savedDish);
        assertEquals(dishModel.getName(), savedDish.getName());
        assertEquals(dishModel.getIdDish(), savedDish.getIdDish());
        assertEquals(dishModel.getPrice(), savedDish.getPrice());
        assertEquals(dishModel.getDescriptionDish(), savedDish.getDescriptionDish());
        assertTrue(savedDish.getStateDish());
        assertEquals(restaurantModel, savedDish.getRestaurantModel());
        assertEquals(categoryModel, savedDish.getCategoryModel());
    }

    @Test
    void test_saveDish_withRequestIdRestaurantNotExists_ShouldThrowInvalidDataException() {
        //Given
        RestaurantModel restaurantModel = new RestaurantModel(2L, "sal", "bellavista",
                "+123456779", "urlLogo", 1L, 12344L);
        CategoryModel categoryModel = new CategoryModel(1L, "salados", "salado");
        DishModel dishModel = new DishModel(2L, "cuscu", "salsa", 12.00,
                "urlImagen", true, restaurantModel, categoryModel);
        // When
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> ownerRestaurantUseCase.saveDish(dishModel));
        //Then
        assertEquals("The restaurant does not exist", exception.getMessage());
    }

    @Test
    void test_saveDish_withRequestIdCategoryNotExists_ShouldThrowInvalidDataException() {
        //Given
        RestaurantModel restaurantModel = new RestaurantModel(1L, "sal", "bellavista",
                "+123456779", "urlLogo", 1L, 12344L);
        CategoryModel categoryModel = new CategoryModel(1L, "salados", "salado");
        DishModel dishModel = new DishModel(2L, "cuscu", "salsa", 12.00,
                "urlImagen", true, restaurantModel, categoryModel);

        when(restaurantPersistencePort.findByIdRestaurant(1L)).thenReturn(restaurantModel);
        // When
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> ownerRestaurantUseCase.saveDish(dishModel));
        // Then
        assertEquals("The category does not exist", exception.getMessage());
    }

    @Test
    void test_saveDish_withRequestPriceIsLessThatZero_ShouldThrowInvalidDataException() {
        //Given
        RestaurantModel restaurantModel = new RestaurantModel(1L, "sal", "bellavista",
                "+123456779", "urlLogo", 1L, 12344L);
        CategoryModel categoryModel = new CategoryModel(1L, "salados", "salado");
        DishModel dishModel = new DishModel(2L, "cuscu", "salsa", -10.5,
                "urlImagen", true, restaurantModel, categoryModel);
        // When
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> ownerRestaurantUseCase.saveDish(dishModel));
        // Then
        assertEquals("Price must be greater than zero", exception.getMessage());
    }

    @Test
    void test_updateDish_WithNonExistingDish_ShouldThrowDishNotExistsException() {
        // Given
        RestaurantModel restaurantModel = new RestaurantModel(1L, "sal", "bellavista",
                "+123456779", "urlLogo", 1L, 12344L);
        CategoryModel categoryModel = new CategoryModel(1L, "salados", "salado");
        DishModel dishModel = new DishModel(1L, "cuscu", "salsa", 20.5,
                "urlImagen", true, restaurantModel, categoryModel);
        DishModel dishNotExist = new DishModel();
        dishNotExist.setIdDish(0L);
        dishNotExist.setPrice(78.00);
        dishNotExist.setDescriptionDish("carne");
        DishModel updatedDish = ownerRestaurantUseCase.updateDish(dishNotExist);

        // When
        DishNotExistsException exception = assertThrows(DishNotExistsException.class, () -> ownerRestaurantUseCase.updateDish(dishModel));

        // Then
        assertEquals("The dish not exist", exception.getMessage());
    }
}
