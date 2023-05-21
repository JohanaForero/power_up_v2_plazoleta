package com.reto.plazoleta.infraestructure.entrypoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reto.plazoleta.application.dto.request.CreateDishRequestDto;
import com.reto.plazoleta.infraestructure.drivenadapter.entity.CategoryEntity;
import com.reto.plazoleta.infraestructure.drivenadapter.entity.RestaurantEntity;
import com.reto.plazoleta.infraestructure.drivenadapter.repository.ICategoryRepository;
import com.reto.plazoleta.infraestructure.drivenadapter.repository.IRestaurantRepository;
import com.reto.plazoleta.infraestructure.exceptionhandler.ExceptionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
class OwnerRestaurantControllerTest {

    private static final String USERNAME_OWNER = "owner@owner.com";
    private static final String PASSWORD_OWNER = "123";
    private static final String ROL_OWNER = "PROPIETARIO";
    private static final String CREATE_DISH = "/services-owner-restaurant/create-dish";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private IRestaurantRepository restaurantRepository;
    @Autowired
    private ICategoryRepository categoryRepository;

    @BeforeEach
    void setup() {
        restaurantRepository.save(new RestaurantEntity(1L, "salado", "bellavista",
                "+123456779", "urlLogo", 108438453L, 15L));
        categoryRepository.save(new CategoryEntity(1L, "salados", "salado"));
    }

    @WithMockUser(username = USERNAME_OWNER, password = PASSWORD_OWNER, roles = {ROL_OWNER})
    @Test
    void test_saveDish_withCreateDishRequestDto_ShouldReturnADishWithStatusCreated() throws Exception {
        CreateDishRequestDto dish = new CreateDishRequestDto("plato1", 20000.00, "description", "http://image.com",
                1L, 1L);

        mockMvc.perform(MockMvcRequestBuilders.post(CREATE_DISH)
                        .content(objectMapper.writeValueAsString(dish))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

    }

    @WithMockUser(username = USERNAME_OWNER, password = PASSWORD_OWNER, roles = {ROL_OWNER})
    @Test
    void test_saveDish_withCreateDishRequestDto_ShouldThrowBadRequestExceptionIfCategoryNotExist() throws Exception {
        CreateDishRequestDto dish = new CreateDishRequestDto("plato1", 20000.00, "description", "http://image.com",
                9L, 1L);

        mockMvc.perform(MockMvcRequestBuilders.post(CREATE_DISH)
                        .content(objectMapper.writeValueAsString(dish))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ExceptionResponse.INVALID_DATA.getMessage()));
    }

    @WithMockUser(username = USERNAME_OWNER, password = PASSWORD_OWNER, roles = {ROL_OWNER})
    @Test
    void test_saveDish_withCreateDishRequestDto_ShouldThrowBadRequestExceptionIfRestaurantNotExist() throws Exception {
        CreateDishRequestDto dish = new CreateDishRequestDto("plato1", 20000.00, "description", "http://image.com",
                1L, 9L);

        mockMvc.perform(MockMvcRequestBuilders.post(CREATE_DISH)
                        .content(objectMapper.writeValueAsString(dish))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ExceptionResponse.INVALID_DATA.getMessage()));
    }

    @WithMockUser(username = USERNAME_OWNER, password = PASSWORD_OWNER, roles = {ROL_OWNER})
    @Test
    void test_saveDish_withZeroPriceInDishCreation_ShouldThrowBadRequestException() throws Exception {
        CreateDishRequestDto dish = new CreateDishRequestDto("plato1", 0.0, "description", "http://image.com",
                1L, 1L);

        mockMvc.perform(MockMvcRequestBuilders.post(CREATE_DISH)
                        .content(objectMapper.writeValueAsString(dish))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ExceptionResponse.INVALID_DATA.getMessage()));
    }

    @WithMockUser(username = USERNAME_OWNER, password = PASSWORD_OWNER, roles = {ROL_OWNER})
    @Test
    void test_saveDish_withNegativePriceInDishCreation_ShouldThrowBadRequestException() throws Exception {
        CreateDishRequestDto dish = new CreateDishRequestDto("plato1", -10.0, "description", "http://image.com",
                1L, 1L);

        mockMvc.perform(MockMvcRequestBuilders.post(CREATE_DISH)
                        .content(objectMapper.writeValueAsString(dish))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ExceptionResponse.INVALID_DATA.getMessage()));
    }
}