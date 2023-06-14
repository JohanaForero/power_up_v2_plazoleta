package com.reto.plazoleta.infraestructure.entrypoint;

import com.reto.plazoleta.application.dto.response.DishOfACategoryResponseDto;
import com.reto.plazoleta.application.dto.response.RestaurantResponsePageableDto;
import com.reto.plazoleta.application.handler.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/micro-small-square/")
public class CustomerController {

    private final ICustomerService customerService;

    @Operation(summary = "List restaurants paginated by a field")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of names and url logo of paginated restaurants"),
            @ApiResponse(responseCode = "403", description = "Role other than customer")
    })
    @PreAuthorize(value = "hasRole('CLIENTE')")
    @GetMapping(value = "restaurants")
    public ResponseEntity<Page<RestaurantResponsePageableDto>> getAllRestaurantsByOrderByNameAsc(
            @Parameter(
                    description = "Number of restaurant items by page",
                    schema = @Schema(implementation = Integer.class))
            @RequestParam(name = "sizeItemsByPages", required = true, defaultValue = "5") Integer sizeItemsByPages) {
        int numberPage = 0;
        return ResponseEntity.ok(customerService.getAllRestaurantsByOrderByNameAsc(numberPage, sizeItemsByPages));
    }

    @PreAuthorize(value = "hasRole('CLIENTE')")
    @GetMapping(value = "menu/restaurant/{id}")
    public ResponseEntity<Page<DishOfACategoryResponseDto>> get(
            @Parameter(
                    description = "Number of restaurant items by page",
                    schema = @Schema(implementation = Integer.class))
            @RequestParam(name = "sizeItemsByPages", defaultValue = "2") Integer sizeItemsByPages,
            @Parameter(
                    description = "Page number",
                    schema = @Schema(implementation = Integer.class))
            @RequestParam(name = "numberPage") Integer numberPage,
            @PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(customerService.getAllDishByOrderByCategory(numberPage, sizeItemsByPages, id));
    }
}
