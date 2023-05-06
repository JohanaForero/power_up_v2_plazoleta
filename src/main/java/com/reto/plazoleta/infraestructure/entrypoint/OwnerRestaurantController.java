package com.reto.plazoleta.infraestructure.entrypoint;

import com.reto.plazoleta.application.dto.request.CreateDishRequestDto;
import com.reto.plazoleta.application.dto.request.UpdateDishRequestDto;
import com.reto.plazoleta.application.handler.IOwnerRestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/services-Owner-restaurant")
public class OwnerRestaurantController {

    private final IOwnerRestaurantService ownerRestaurantService;

    @Operation(summary = "Add a new Dish")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dish created", content = @Content),
            @ApiResponse(responseCode = "400", description = "The format in the fields is invalid", content = @Content),
            @ApiResponse(responseCode = "409", description = "There are empty fields", content = @Content)
    })
    @PreAuthorize(value = "hasRole('PROPIETARIO')")
    @PostMapping(value = "/")
    public ResponseEntity<Void> saveDish(@RequestBody CreateDishRequestDto createDishRequestDto) {
        ownerRestaurantService.saveDish(createDishRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PutMapping(value = "/")
    public ResponseEntity<Void> updateDish(@RequestBody UpdateDishRequestDto updateDishRequestDto) {
        ownerRestaurantService.updateDish(updateDishRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
