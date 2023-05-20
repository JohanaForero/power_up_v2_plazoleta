package com.reto.plazoleta.infraestructure.entrypoint;

import com.reto.plazoleta.infraestructure.drivenadapter.entity.RestaurantEntity;
import com.reto.plazoleta.infraestructure.drivenadapter.repository.IRestaurantRepository;
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

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IRestaurantRepository restaurantRepository;

    @BeforeEach
    void setup() {
        List<RestaurantEntity> restaurantList = new ArrayList<>();
        restaurantList.add(new RestaurantEntity(1L, "Restaurante 1", "Dirección 1", "3014896273", "http://restaurante1.com", 111111L, 1L));
        restaurantList.add(new RestaurantEntity(2L, "Restaurante 2", "Dirección 2", "3224196283", "http://restaurante2.com", 222222L, 2L));
        restaurantRepository.saveAll(restaurantList);
    }

    @WithMockUser(username = "customer@customer.com", password = "123", roles = {"CLIENTE"})
    @Test
    void test_getAllRestaurantsByOrderByNameAsc_withIntAsSizeItemsByPages_ShouldResponseAListOfNameAndUrlLogoOfRestaurantsPageableByPageSizeOrderByNameAsc() throws Exception {
        mockMvc.perform(get("/micro-small-square/restaurants")
                        .param("sizeItemsByPages", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageable.pageSize").value(10))
                .andExpect(jsonPath("$.pageable.pageNumber").value(0))
                .andExpect(jsonPath("$.content[0].name").value("Restaurante 1"))
                .andExpect(jsonPath("$.content[0].urlLogo").value("http://restaurante1.com"))
                .andExpect(jsonPath("$.content[1].name").value("Restaurante 2"))
                .andExpect(jsonPath("$.content[1].urlLogo").value("http://restaurante2.com"));
    }

    @WithMockUser(username = "admin@dmin.com", password = "123", roles = {"ADMINISTRADOR"})
    @Test
    void test_getAllRestaurantsByOrderByNameAsc_withRoleAdminInTheToken_ShouldThrowAStatusForbidden() throws Exception {
        mockMvc.perform(get("/micro-small-square/restaurants")
                        .param("sizeItemsByPages", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("Prohibited you do not have the necessary role for authorization"));
    }

    @WithMockUser(username = "customer@customer.com", password = "123", roles = {"CLIENTE"})
    @Test
    void test_getAllRestaurantsByOrderByNameAsc_withPageSizeOneAndTwoRestaurantsInDatabase_ShouldReturnSuccessAndTwoTotalPagesAndARestaurant() throws Exception {
        mockMvc.perform(get("/micro-small-square/restaurants")
                        .param("sizeItemsByPages", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageable.pageSize").value(1))
                .andExpect(jsonPath("$.pageable.pageNumber").value(0))
                .andExpect(jsonPath("$.totalPages").value(2))
                .andExpect(jsonPath("$.content[0].name").value("Restaurante 1"))
                .andExpect(jsonPath("$.content[0].urlLogo").value("http://restaurante1.com"));
    }
}