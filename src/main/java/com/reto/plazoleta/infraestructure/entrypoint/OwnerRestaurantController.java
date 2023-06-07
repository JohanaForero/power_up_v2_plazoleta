package com.reto.plazoleta.infraestructure.entrypoint;

import com.reto.plazoleta.application.dto.request.CreateDishRequestDto;
import com.reto.plazoleta.application.dto.request.UpdateDishRequestDto;
import com.reto.plazoleta.application.dto.request.UpdateStateDishRequestDto;
import com.reto.plazoleta.application.dto.response.CreateDishResponseDto;
import com.reto.plazoleta.application.dto.response.UpdateDishResponseDto;
import com.reto.plazoleta.application.dto.response.UpdateStateDishResponseDto;
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
    @PostMapping(value = "/create-dish")
    public ResponseEntity<CreateDishResponseDto> saveDish(@RequestBody CreateDishRequestDto createDishRequestDto) {
        CreateDishResponseDto responseDto = ownerRestaurantService.saveDish(createDishRequestDto);
        return new ResponseEntity<>(responseDto,HttpStatus.CREATED);
    }

    @Operation(summary = "update dish price and description")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Price and description update", content = @Content),
            @ApiResponse(responseCode = "401", description = "The format in the fields is invalid", content = @Content),
            @ApiResponse(responseCode = "403", description = "no access allowed", content = @Content)
    })
    @PatchMapping(value = "/update-dish")
    @PreAuthorize(value = "hasRole('PROPIETARIO')")
    public ResponseEntity<UpdateDishResponseDto> updateDishPriceAndDescription(@RequestBody UpdateDishRequestDto updateDishRequestDto) {
        UpdateDishResponseDto dishResponseDto = ownerRestaurantService.updateDish(updateDishRequestDto);
        return new ResponseEntity<>(dishResponseDto,HttpStatus.OK);
    }

    @Operation(summary = "update dish state")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Price and description update", content = @Content),
            @ApiResponse(responseCode = "400", description = "The format in the fields is invalid", content = @Content),
            @ApiResponse(responseCode = "403", description = "no access allowed", content = @Content)
    })
    @PatchMapping(value = "/update-state-dish")
    @PreAuthorize(value = "hasRole('PROPIETARIO')")
    public ResponseEntity<UpdateStateDishResponseDto> UpdateStateDish(@RequestBody UpdateStateDishRequestDto updateStateDishRequestDto) {
        UpdateStateDishResponseDto stateDishResponseDto = ownerRestaurantService.updateStateDish(updateStateDishRequestDto);
        return new ResponseEntity<>(stateDishResponseDto,HttpStatus.OK);
    }
}