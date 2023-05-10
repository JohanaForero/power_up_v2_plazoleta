package com.reto.plazoleta.infraestructure.entrypoint;

import com.reto.plazoleta.application.dto.request.CreateDishRequestDto;
import com.reto.plazoleta.application.dto.request.UpdateDishRequestDto;
import com.reto.plazoleta.application.dto.request.UpdateDishStateRequestDto;
import com.reto.plazoleta.application.dto.response.UpdateDishResponseDto;
import com.reto.plazoleta.application.dto.response.UpdateDishStateResponseDto;
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
    @PostMapping(value = "/")
    public ResponseEntity<Void> saveDish(@RequestBody CreateDishRequestDto createDishRequestDto) {
        ownerRestaurantService.saveDish(createDishRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "update dish price and description")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Price and description update", content = @Content),
            @ApiResponse(responseCode = "400", description = "The format in the fields is invalid", content = @Content),
            @ApiResponse(responseCode = "409", description = "There are empty fields", content = @Content)
    })
    @PutMapping(value = "/dish")
    @PreAuthorize(value = "hasRole('PROPIETARIO')")
    public ResponseEntity<UpdateDishResponseDto> updateDishPriceAndDescription(@RequestBody UpdateDishRequestDto updateDishRequestDto) {
        return new ResponseEntity<>(ownerRestaurantService.updateDish(updateDishRequestDto), HttpStatus.OK);
    }

    @Operation(summary = "update plate status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "State update", content = @Content),
            @ApiResponse(responseCode = "400", description = "The format in the fields is invalid", content = @Content),
            @ApiResponse(responseCode = "409", description = "There are empty fields", content = @Content)
    })
    @PutMapping(value = "/")
    @PreAuthorize(value = "hasRole('PROPIETARIO')")
    public ResponseEntity<UpdateDishStateResponseDto> updateDishState(@RequestBody UpdateDishStateRequestDto updateDishStateRequestDto) {
        return new ResponseEntity<>(ownerRestaurantService.updateDishState(updateDishStateRequestDto), HttpStatus.OK);
    }

}
