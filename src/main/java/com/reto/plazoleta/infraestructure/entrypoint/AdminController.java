package com.reto.plazoleta.infraestructure.entrypoint;

import com.reto.plazoleta.application.dto.RequestToCreateRestaurantDto;
import com.reto.plazoleta.application.handler.IAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@RequestMapping("/micro-small-square/restaurant")
public class AdminController {

    private final IAdminService adminService;

    @PreAuthorize(value = "hasRole('ADMINISTRADOR')")
    @Operation(summary = "Add a new Restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurant created", content = @Content),
            @ApiResponse(responseCode = "400", description = "The format in the fields is invalid", content = @Content),
            @ApiResponse(responseCode = "409", description = "There are empty fields", content = @Content)
    })
    @PostMapping(value = "/")
    public ResponseEntity<Void> saveRestaurant(@Parameter(
            description = "The restaurant object to create",
            required = true,
            schema = @Schema(implementation = RequestToCreateRestaurantDto.class))
            @RequestBody RequestToCreateRestaurantDto requestToCreateRestaurantDto) {
        adminService.saveRestaurant(requestToCreateRestaurantDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
