package com.reto.plazoleta.infraestructure.entrypoint;

import com.reto.plazoleta.application.dto.request.CreateDishRequestDto;
import com.reto.plazoleta.application.dto.request.RestaurantEmployeeRequestDto;
import com.reto.plazoleta.application.dto.response.CreateDishResponseDto;
import com.reto.plazoleta.application.dto.response.RestaurantEmployeeResponseDto;
import com.reto.plazoleta.application.handler.IOwnerRestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/services-owner-restaurant")
public class OwnerRestaurantController {

    private final IOwnerRestaurantService ownerRestaurantService;

    @PreAuthorize(value = "hasRole('PROPIETARIO')")
    @Operation(summary = "Add a new Dish")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dish created", content = @Content),
            @ApiResponse(responseCode = "400", description = "The format in the fields is invalid", content = @Content),
            @ApiResponse(responseCode = "409", description = "There are empty fields", content = @Content)
    })
    @PostMapping(value = "/create-dish")
    public ResponseEntity<CreateDishResponseDto> saveDish(@RequestBody CreateDishRequestDto createDishRequestDto) {
        CreateDishResponseDto responseDto = ownerRestaurantService.saveDish(createDishRequestDto);
        return new ResponseEntity<>(responseDto,HttpStatus.CREATED);
    }

    @Operation(summary = "Add a new User employee in a restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "restaurant employee created"),
            @ApiResponse(responseCode = "404", description = "The restaurant not found")
    })
    @PreAuthorize(value = "hasRole('PROPIETARIO')")
    @PostMapping(value = "/add-employee-restaurant")
    public ResponseEntity<RestaurantEmployeeResponseDto> saveUserEmployeeInARestaurant(@RequestBody RestaurantEmployeeRequestDto restaurantEmployeeRequestDto) {
        final RestaurantEmployeeResponseDto restaurantEmployeeCreated = this.ownerRestaurantService.saveUserEmployeeInTheRestaurant(restaurantEmployeeRequestDto);
        return new ResponseEntity<>(restaurantEmployeeCreated, HttpStatus.CREATED);
    }
}