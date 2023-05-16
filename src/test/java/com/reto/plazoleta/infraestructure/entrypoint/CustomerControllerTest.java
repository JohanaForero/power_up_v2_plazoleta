package com.reto.plazoleta.infraestructure.entrypoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CustomerControllerTest {

    private final WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    CustomerControllerTest(WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
    }

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @WithMockUser(username = "customer@customer.com", password = "123", roles = {"CLIENTE"})
    @Test
    void test_getAllRestaurantsByOrderByNameAsc_withIntAsSizeItemsByPages_whenSystemListRestaurantPageableByField_ShouldReturnAListRestaurantPageableWithTheFieldsNameAndUrlLogo() throws Exception {
        mockMvc.perform(get("/micro-small-square/customer")
                        .param("sizeItemsByPages", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageable.pageSize").value(10))
                .andExpect(jsonPath("$.pageable.pageNumber").value(0))
                .andExpect(jsonPath("$.content.length()").value(0));
    }
}