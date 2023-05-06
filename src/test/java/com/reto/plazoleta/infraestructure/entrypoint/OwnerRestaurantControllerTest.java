package com.reto.plazoleta.infraestructure.entrypoint;

import com.reto.plazoleta.application.handler.IOwnerRestaurantService;
import com.reto.plazoleta.application.handler.impl.OwnerRestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OwnerRestaurantControllerTest {
    private IOwnerRestaurantService ownerRestaurantService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void saveDish() {
    }

    @Test
    void updateDish() {
    }
}