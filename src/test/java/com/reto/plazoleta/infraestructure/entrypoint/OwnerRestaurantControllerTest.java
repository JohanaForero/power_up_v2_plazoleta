package com.reto.plazoleta.infraestructure.entrypoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reto.plazoleta.application.dto.request.CreateDishRequestDto;
import com.reto.plazoleta.application.dto.request.RequestEmployeeAccountDto;
import com.reto.plazoleta.application.dto.request.UpdateDishRequestDto;
import com.reto.plazoleta.application.dto.request.UpdateStateDishRequestDto;
import com.reto.plazoleta.domain.gateways.IUserGateway;
import com.reto.plazoleta.infraestructure.configuration.security.jwt.JwtProvider;
import com.reto.plazoleta.infraestructure.drivenadapter.entity.CategoryEntity;
import com.reto.plazoleta.infraestructure.drivenadapter.entity.DishEntity;
import com.reto.plazoleta.infraestructure.drivenadapter.entity.RestaurantEntity;
import com.reto.plazoleta.infraestructure.drivenadapter.gateways.User;
import com.reto.plazoleta.infraestructure.drivenadapter.repository.ICategoryRepository;
import com.reto.plazoleta.infraestructure.drivenadapter.repository.IDishRepository;
import com.reto.plazoleta.infraestructure.drivenadapter.repository.IRestaurantRepository;
import com.reto.plazoleta.infraestructure.exceptionhandler.ExceptionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OwnerRestaurantControllerTest {

    private static final String USERNAME_OWNER = "owner@owner.com";
    private static final String PASSWORD_OWNER = "123";
    private static final String ROL_OWNER = "PROPIETARIO";
    private static final String CREATE_DISH = "/services-owner-restaurant/dish";
    private static final String UPDATE_DISH = "/services-owner-restaurant/dish";
    private static final String UPDATE_STATE_DISH = "/services-owner-restaurant/status-dish";
    private static final String ADD_A_EMPLOYEE_TO_THE_RESTAURANT = "/services-owner-restaurant/employee";
    private static final String TOKEN_VALID = "Bearer + token";
    private static final String EMAIL_TAKEN_FROM_TOKEN = "owner@owner.com";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IRestaurantRepository restaurantRepository;

    @Autowired
    private ICategoryRepository categoryRepository;

    @Autowired
    private IDishRepository dishRepository;

    @MockBean
    private IUserGateway userGateway;

    @MockBean
    private JwtProvider jwtProvider;

    @BeforeEach
    void setup() {
        restaurantRepository.save(new RestaurantEntity(1L, "salado", "bellavista",
                "+123456779", "urlLogo", 108438453L, 15L));
        categoryRepository.save(new CategoryEntity(1L, "salados", "salado"));
        dishRepository.save(new DishEntity(1L, "name", "description", 300000.0, "http://image.com", true, new RestaurantEntity(1L, "salado",
                "bellavista", "+123456779", "urlLogo", 108438453L, 15L), new CategoryEntity(1L, "salados", "salado")));
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
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

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

    @WithMockUser(username = USERNAME_OWNER, password = PASSWORD_OWNER, roles = {ROL_OWNER})
    @Test
    void test_updateStateDish_withUpdateStateDishRequestDtoComplete_ShouldReturnAUpdateStateDishResponseDtoAndStatusOk() throws Exception {
        final DishEntity dishEntitySaved = this.dishRepository.save(new DishEntity(1L,"plato1", "description", 20000.00, "http://image.com",true, new RestaurantEntity(1L, "salado", "bellavista",
                "+123456779", "urlLogo", 108438453L, 15L), new CategoryEntity(1L, "salados", "salado")));

        UpdateStateDishRequestDto updateStateDishRequestDto = new UpdateStateDishRequestDto(1L, 1L, false);

        mockMvc.perform(MockMvcRequestBuilders.patch(UPDATE_STATE_DISH)
                        .content(objectMapper.writeValueAsString(updateStateDishRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idDish").value(1));

        assertEquals(dishEntitySaved.getIdDish(), updateStateDishRequestDto.getIdDish());
        assertEquals(dishEntitySaved.getRestaurantEntity().getIdRestaurant(), updateStateDishRequestDto.getIdRestaurant());
    }

    @WithMockUser(username = USERNAME_OWNER, password = PASSWORD_OWNER, roles = {ROL_OWNER})
    @Test
    void test_updateStateDish_withInvalidOwnerRestaurant_ShouldThrowInvalidDataExceptionOwnerRestaurantNotPermitted() throws Exception {
        RestaurantEntity restaurantOwnerDish = new RestaurantEntity(2L, "salado", "bellavista", "+123456779", "urlLogo", 108438453L, 15L);

        DishEntity dish = new DishEntity(1L, "name", "descriptionDish", 15000.0, "http://imagen.jpeg", true, restaurantOwnerDish, new CategoryEntity(1L, "salados", "salado"));
        restaurantRepository.save(restaurantOwnerDish);
        dishRepository.save(dish);
        UpdateStateDishRequestDto updateStateDishRequestDto = new UpdateStateDishRequestDto(1L, 1L, false);

        mockMvc.perform(MockMvcRequestBuilders.patch(UPDATE_STATE_DISH)
                        .content(objectMapper.writeValueAsString(updateStateDishRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ExceptionResponse.INVALID_DATA.getMessage()));
    }

    @WithMockUser(username = USERNAME_OWNER, password = PASSWORD_OWNER, roles = {ROL_OWNER})
    @Test
    void test_updateDish_withUpdateDishRequestDto_ShouldStatusOk() throws Exception {
        final DishEntity dishSavedEntityExpected = this.dishRepository.save(new DishEntity(1L, "name", "descriptionDish", 15000.0, "http://imagen.jpeg",
                true, new RestaurantEntity(1L, "salado", "bellavista", "+123456779", "urlLogo", 108438453L, 15L),
                new CategoryEntity(1L, "salados", "salado")));
        UpdateDishRequestDto updateDishRequestDto = new UpdateDishRequestDto(1L, 1L, 20.0, "description");

        mockMvc.perform(MockMvcRequestBuilders.patch(UPDATE_DISH)
                        .content(objectMapper.writeValueAsString(updateDishRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idDish").value(1));

        assertEquals(dishSavedEntityExpected.getIdDish(), updateDishRequestDto.getIdDish());
        assertEquals(dishSavedEntityExpected.getRestaurantEntity().getIdRestaurant(), updateDishRequestDto.getIdRestaurant());
    }

    @WithMockUser(username = USERNAME_OWNER, password = PASSWORD_OWNER, roles = {ROL_OWNER})
    @Test
    void test_updateDish_withInvalidRestaurant_ShouldThrowInvalidDataExceptionRestaurantNotPermitted() throws Exception {
        RestaurantEntity restaurantOwnerDish = new RestaurantEntity(2L, "salado", "bellavista", "+123456779", "urlLogo", 108438453L, 15L);

        DishEntity dish = new DishEntity(1L, "name", "descriptionDish", 15000.0, "http://imagen.jpeg", true, restaurantOwnerDish, new CategoryEntity(1L, "salados", "salado"));
        restaurantRepository.save(restaurantOwnerDish);
        dishRepository.save(dish);

        UpdateDishRequestDto updateDish = new UpdateDishRequestDto(1L, 1L, 20.0, "description");

        mockMvc.perform(MockMvcRequestBuilders.patch(UPDATE_DISH)
                        .content(objectMapper.writeValueAsString(updateDish))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ExceptionResponse.INVALID_DATA.getMessage()));
    }

    @WithMockUser(username = USERNAME_OWNER, password = PASSWORD_OWNER, roles = {ROL_OWNER})
    @Test
    void test_saveUserEmployeeInARestaurant_withAllFieldsCompleteAndValidInTheObjectAsRestaurantEmployeeRequestDto_shouldReturnAStatusCreatedAndTheIdRestaurantEmployee() throws Exception {
        RequestEmployeeAccountDto restaurantEmployeeRequest = new RequestEmployeeAccountDto();
        restaurantEmployeeRequest.setIdRestaurant(1L);
        restaurantEmployeeRequest.setIdUserEmployee(2L);
        User userFoundByToken = new User();
        userFoundByToken.setIdUser(15L);
        when(this.userGateway.getUserByEmailInTheToken(EMAIL_TAKEN_FROM_TOKEN, TOKEN_VALID)).thenReturn(userFoundByToken);
        when(this.jwtProvider.getAuthentication(TOKEN_VALID.replace("Bearer ", ""))).thenReturn(new UsernamePasswordAuthenticationToken(EMAIL_TAKEN_FROM_TOKEN, null, null));
        this.mockMvc.perform(MockMvcRequestBuilders.post(ADD_A_EMPLOYEE_TO_THE_RESTAURANT)
                        .header(HttpHeaders.AUTHORIZATION, TOKEN_VALID)
                        .content(objectMapper.writeValueAsString(restaurantEmployeeRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idRestaurantEmployee").value(1));
    }

    @WithMockUser(username = USERNAME_OWNER, password = PASSWORD_OWNER, roles = {ROL_OWNER})
    @Test
    void test_saveUserEmployeeInARestaurant_withFieldIdRestaurantThatIsNotRelatedToARestaurantRequestedInTheParameter_shouldReturnAStatusNotFound() throws Exception {
        RequestEmployeeAccountDto restaurantEmployeeRequestWhereIdOwnerRestaurantDoesNotHaveARestaurant = new RequestEmployeeAccountDto();
        restaurantEmployeeRequestWhereIdOwnerRestaurantDoesNotHaveARestaurant.setIdRestaurant(0L);
        restaurantEmployeeRequestWhereIdOwnerRestaurantDoesNotHaveARestaurant.setIdUserEmployee(2L);
        User userFoundByToken = new User();
        userFoundByToken.setIdUser(18L);
        when(this.userGateway.getUserByEmailInTheToken(USERNAME_OWNER, TOKEN_VALID)).thenReturn(userFoundByToken);
        when(this.jwtProvider.getAuthentication(TOKEN_VALID.replace("Bearer ", ""))).thenReturn(new UsernamePasswordAuthenticationToken(EMAIL_TAKEN_FROM_TOKEN, null, null));
        this.mockMvc.perform(MockMvcRequestBuilders.post(ADD_A_EMPLOYEE_TO_THE_RESTAURANT)
                        .header(HttpHeaders.AUTHORIZATION, TOKEN_VALID)
                        .content(objectMapper.writeValueAsString(restaurantEmployeeRequestWhereIdOwnerRestaurantDoesNotHaveARestaurant))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(ExceptionResponse.OBJECT_NOT_FOUND.getMessage()));
    }
}