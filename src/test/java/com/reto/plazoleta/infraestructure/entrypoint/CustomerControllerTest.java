package com.reto.plazoleta.infraestructure.entrypoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reto.plazoleta.application.dto.request.CreateOrderRequestDto;
import com.reto.plazoleta.application.dto.request.DishFromOrderAndAmountRequestDto;
import com.reto.plazoleta.infraestructure.drivenadapter.entity.CategoryEntity;
import com.reto.plazoleta.infraestructure.drivenadapter.entity.DishEntity;
import com.reto.plazoleta.infraestructure.drivenadapter.entity.OrderEntity;
import com.reto.plazoleta.infraestructure.drivenadapter.entity.RestaurantEntity;
import com.reto.plazoleta.infraestructure.drivenadapter.repository.IDishRepository;
import com.reto.plazoleta.infraestructure.drivenadapter.repository.IOrderRepository;
import com.reto.plazoleta.infraestructure.drivenadapter.repository.IRestaurantRepository;
import com.reto.plazoleta.infraestructure.exceptionhandler.ExceptionResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IRestaurantRepository restaurantRepository;

    @Autowired
    private IDishRepository dishRepository;

    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private List<DishEntity> listDishEntities;

    private static final String USERNAME_CUSTOMER = "customer@customer.com";
    private static final String USERNAME_ADMIN = "admin@dmin.com";
    private static final String PASSWORD = "123";
    private static final String ROLE_ADMIN = "ADMINISTRADOR";
    private static final String ROLE_CUSTOMER = "CLIENTE";
    private static final String RESTAURANT_API_PATH = "/micro-small-square/restaurants";
    private static final String PAGE_SIZE_PARAM = "sizeItemsByPages";
    private static final String REGISTER_ORDER_API_PATH = "/micro-small-square/make-an-order";
    private static final String TOKE_WITH_PREFIX_BEARER = "Bearer + token";

    @BeforeAll
    void initializeTestEnvironment() {
        List<RestaurantEntity> restaurantList = new ArrayList<>();
        restaurantList.add(new RestaurantEntity(1L, "Restaurante 1", "Dirección 1", "3014896273", "http://restaurante1.com", 111111L, 1L));
        restaurantList.add(new RestaurantEntity(2L, "Restaurante 2", "Dirección 2", "3224196283", "http://restaurante2.com", 222222L, 2L));
        restaurantRepository.saveAll(restaurantList);

        this.listDishEntities = new ArrayList<>();
        final CategoryEntity categoryEntityToSave = new CategoryEntity(1L, "Plato tipico", "Comida real");
        listDishEntities.add(new DishEntity(1L, "BOWL MONTAÑERO", "Carne desmechada, Lentejas en guiso con salchichas", 35000.0, "http://image.png",
                true, restaurantList.get(0), categoryEntityToSave));
        listDishEntities.add(new DishEntity(2L, "BOWL DE POLLO", "Pechuga de pollo bañada en hongo", 45000.0, "http://image.png",
                true, restaurantList.get(0), categoryEntityToSave));
        listDishEntities.add(new DishEntity(3L, "BOWL PAISA", "Deliciosos chicarrones, frijoles, arroz blanco", 49000.0, "http://image.png",
                true, restaurantList.get(1), categoryEntityToSave));
        this.dishRepository.saveAll(listDishEntities);

        this.orderRepository.save(new OrderEntity());
    }

    @WithMockUser(username = USERNAME_CUSTOMER, password = PASSWORD, roles = {ROLE_CUSTOMER})
    @Test
    void test_getAllRestaurantsByOrderByNameAsc_withIntAsSizeItemsByPages_ShouldResponseAListOfNameAndUrlLogoOfRestaurantsPageableByPageSizeOrderByNameAsc() throws Exception {
        mockMvc.perform(get(RESTAURANT_API_PATH)
                        .param(PAGE_SIZE_PARAM, "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageable.pageSize").value(10))
                .andExpect(jsonPath("$.pageable.pageNumber").value(0))
                .andExpect(jsonPath("$.content[0].name").value("Restaurante 1"))
                .andExpect(jsonPath("$.content[0].urlLogo").value("http://restaurante1.com"))
                .andExpect(jsonPath("$.content[1].name").value("Restaurante 2"))
                .andExpect(jsonPath("$.content[1].urlLogo").value("http://restaurante2.com"));
    }

    @WithMockUser(username = USERNAME_ADMIN, password = PASSWORD, roles = {ROLE_ADMIN})
    @Test
    void test_getAllRestaurantsByOrderByNameAsc_withRoleAdminInTheToken_ShouldThrowAStatusForbidden() throws Exception {
        mockMvc.perform(get(RESTAURANT_API_PATH)
                        .param(PAGE_SIZE_PARAM, "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(ExceptionResponse.ACCESS_DENIED.getMessage()));
    }

    @WithMockUser(username = USERNAME_CUSTOMER, password = PASSWORD, roles = {ROLE_CUSTOMER})
    @Test
    void test_getAllRestaurantsByOrderByNameAsc_withPageSizeOneAndTwoRestaurantsInDatabase_ShouldReturnSuccessAndTwoTotalPagesAndARestaurant() throws Exception {
        mockMvc.perform(get(RESTAURANT_API_PATH)
                        .param(PAGE_SIZE_PARAM, "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageable.pageSize").value(1))
                .andExpect(jsonPath("$.pageable.pageNumber").value(0))
                .andExpect(jsonPath("$.totalPages").value(2))
                .andExpect(jsonPath("$.content[0].name").value("Restaurante 1"))
                .andExpect(jsonPath("$.content[0].urlLogo").value("http://restaurante1.com"));
    }

    @Transactional
    @WithMockUser(username = USERNAME_CUSTOMER, password = PASSWORD, roles = {ROLE_CUSTOMER})
    @Test
    void test_getAllRestaurantsByOrderByNameAsc_withPageSizeOneAndNotDataFound_ShouldThrowAStatusNoContent() throws Exception {
        restaurantRepository.deleteAll();
        mockMvc.perform(get(RESTAURANT_API_PATH)
                        .param(PAGE_SIZE_PARAM, "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @WithMockUser(username = USERNAME_CUSTOMER, password = PASSWORD, roles = {ROLE_CUSTOMER})
    @Test
    void test_getAllRestaurantsByOrderByNameAsc_withNullPageSizeAndByDefaultTheValueIsFive_ShouldReturnMaximumFivePaginatedRestaurants() throws Exception {
        mockMvc.perform(get(RESTAURANT_API_PATH)
                        .param(PAGE_SIZE_PARAM, "")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageable.pageSize").value(5))
                .andExpect(jsonPath("$.pageable.pageNumber").value(0));
    }

    @Transactional
    @WithMockUser(username = USERNAME_CUSTOMER, password = PASSWORD, roles = {ROLE_CUSTOMER})
    @Test
    void test_registerOrderFromCustomer_withTheFieldsCompleteFromCreateOrderRequestDtoAndTokenValid_ShouldResponseValueFromFieldIdOrderSavedInTheDataBase() throws Exception {
        this.orderRepository.deleteAll();
        List<DishFromOrderAndAmountRequestDto> listDishAndAmountRequest = new ArrayList<>();
        listDishAndAmountRequest.add(new DishFromOrderAndAmountRequestDto(listDishEntities.get(0).getIdDish(), listDishEntities.get(0).getName(), 4));
        listDishAndAmountRequest.add(new DishFromOrderAndAmountRequestDto(listDishEntities.get(1).getIdDish(), listDishEntities.get(1).getName(), 2));
        CreateOrderRequestDto createOrderRequest = new CreateOrderRequestDto(1L, listDishAndAmountRequest);
        this.mockMvc.perform(post(REGISTER_ORDER_API_PATH)
                        .header(HttpHeaders.AUTHORIZATION, TOKE_WITH_PREFIX_BEARER)
                        .content(this.objectMapper.writeValueAsString(createOrderRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idOrder").value(1));
    }

    @WithMockUser(username = USERNAME_CUSTOMER, password = PASSWORD, roles = {ROLE_CUSTOMER})
    @Test
    void test_registerOrderFromCustomer_withAndTheFieldsCompleteFromCreateOrderRequestDtoButUserHasAnOrderInProcessAndTokenValid_ShouldResponseAStatusConflict() throws Exception {
        List<DishFromOrderAndAmountRequestDto> listDishAndAmountRequest = new ArrayList<>();
        listDishAndAmountRequest.add(new DishFromOrderAndAmountRequestDto(listDishEntities.get(0).getIdDish(), listDishEntities.get(0).getName(), 4));
        listDishAndAmountRequest.add(new DishFromOrderAndAmountRequestDto(listDishEntities.get(1).getIdDish(), listDishEntities.get(1).getName(), 2));
        CreateOrderRequestDto createOrderRequest = new CreateOrderRequestDto(1L, listDishAndAmountRequest);
        this.mockMvc.perform(post(REGISTER_ORDER_API_PATH)
                        .header(HttpHeaders.AUTHORIZATION, TOKE_WITH_PREFIX_BEARER)
                        .content(this.objectMapper.writeValueAsString(createOrderRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(ExceptionResponse.ORDER_IN_PROCESS));
    }

    @WithMockUser(username = USERNAME_CUSTOMER, password = PASSWORD, roles = {ROLE_CUSTOMER})
    @Test
    void test_registerOrderFromCustomer_withFieldIdRestaurantInTheObjectAsCreateOrderRequestDtoNotExistsInTheDataBaseAndTokenValid_ShouldResponseAStatusNotFound() throws Exception {
        List<DishFromOrderAndAmountRequestDto> listDishAndAmountRequest = new ArrayList<>();
        listDishAndAmountRequest.add(new DishFromOrderAndAmountRequestDto(listDishEntities.get(0).getIdDish(), listDishEntities.get(0).getName(), 4));
        listDishAndAmountRequest.add(new DishFromOrderAndAmountRequestDto(listDishEntities.get(1).getIdDish(), listDishEntities.get(1).getName(), 2));
        CreateOrderRequestDto createOrderRequest = new CreateOrderRequestDto(3L, listDishAndAmountRequest);
        this.mockMvc.perform(post(REGISTER_ORDER_API_PATH)
                        .header(HttpHeaders.AUTHORIZATION, TOKE_WITH_PREFIX_BEARER)
                        .content(this.objectMapper.writeValueAsString(createOrderRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(ExceptionResponse.OBJECT_NOT_FOUND));
    }

    @WithMockUser(username = USERNAME_CUSTOMER, password = PASSWORD, roles = {ROLE_CUSTOMER})
    @Test
    void test_registerOrderFromCustomer_withObjectAsCreateOrderRequestDtoWhereFieldIdDishNotExistsInTheDataBaseAndTokenValid_ShouldResponseAStatusNotFound() throws Exception {
        List<DishFromOrderAndAmountRequestDto> listDishAndAmountRequest = new ArrayList<>();
        listDishAndAmountRequest.add(new DishFromOrderAndAmountRequestDto(10L, listDishEntities.get(0).getName(), 4));
        listDishAndAmountRequest.add(new DishFromOrderAndAmountRequestDto(listDishEntities.get(1).getIdDish(), listDishEntities.get(1).getName(), 2));
        CreateOrderRequestDto createOrderRequest = new CreateOrderRequestDto(1L, listDishAndAmountRequest);
        this.mockMvc.perform(post(REGISTER_ORDER_API_PATH)
                        .header(HttpHeaders.AUTHORIZATION, TOKE_WITH_PREFIX_BEARER)
                        .content(this.objectMapper.writeValueAsString(createOrderRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(ExceptionResponse.DISH_NOT_EXISTS));
    }

    @WithMockUser(username = USERNAME_CUSTOMER, password = PASSWORD, roles = {ROLE_CUSTOMER})
    @Test
    void testRegisterOrderFromCustomer_WithAllFieldsCompleteAndTokenValid_ShouldResponseAStatusCreated() throws Exception {
    }

    @WithMockUser(username = USERNAME_CUSTOMER, password = PASSWORD, roles = {ROLE_CUSTOMER})
    @Test
    void test_registerOrderFromCustomer_withExistingInProgressOrderAndTokenValid_ShouldReturnStatusConflict() throws Exception {
    }

    @WithMockUser(username = USERNAME_CUSTOMER, password = PASSWORD, roles = {ROLE_CUSTOMER})
    @Test
    void test_registerOrderFromCustomer_WithNonexistentRestaurantIdAndValidToken_ShouldReturnStatusNotFound() throws Exception {
    }

    @WithMockUser(username = USERNAME_CUSTOMER, password = PASSWORD, roles = {ROLE_CUSTOMER})
    @Test
    void test_registerOrderFromCustomer_withNonexistentDishIdAndValidToken_ShouldReturnStatusNotFound()  throws Exception {
    }
}