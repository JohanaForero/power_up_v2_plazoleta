package com.reto.plazoleta.infraestructure.entrypoint;

import com.reto.plazoleta.application.dto.request.CreateOrderRequestDto;
import com.reto.plazoleta.application.dto.request.OrderWithASingleDishDto;
import com.reto.plazoleta.application.dto.response.*;
import com.reto.plazoleta.application.handler.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Page<RestaurantResponsePaginatedDto>> getAllRestaurantsByOrderByNameAsc(
            @Parameter(
                    description = "Number of restaurant items by page",
                    schema = @Schema(implementation = Integer.class))
            @RequestParam(name = "sizeItemsByPages", required = true, defaultValue = "5") Integer sizeItemsByPages) {
        int numberPage = 0;
        return ResponseEntity.ok(customerService.getAllRestaurantsByOrderByNameAsc(numberPage, sizeItemsByPages));
    }

    @Operation(summary = "get dishes paginated by a number of elements and grouped by category from a restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "list from dishes from a restaurant grouped by category"),
            @ApiResponse(responseCode = "204", description = "No data found"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    @GetMapping(value = "/restaurant/{idRestaurant}")
    @PreAuthorize(value = "hasRole('CLIENTE')")
    public ResponseEntity<Page<CategoryFromDishesPaginatedResponseDto>> getDishesFromARestaurantAndGroupedByCategoryPaginated(@RequestParam(name = "sizePage", defaultValue = "5") Integer sizeItems,
                                                                                                                              @PathVariable(name = "idRestaurant") Long idRestaurant, @RequestParam(name = "numberPage", defaultValue = "0") Integer numberPage) {
        return ResponseEntity.ok(this.customerService.getDishesFromARestaurantAndGroupedByCategoryPaginated(numberPage, sizeItems, idRestaurant));
    }

    @Operation(summary = "order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registered order"),
            @ApiResponse(responseCode = "403", description = "Role other than customer"),
            @ApiResponse(responseCode = "404", description = "The Dish not exists"),
            @ApiResponse(responseCode = "404", description = "The Restaurant not exists"),
            @ApiResponse(responseCode = "409", description = "The customer has a order in process")
    })
    @PreAuthorize(value = "hasRole('CLIENTE')")
    @PostMapping(value = "order")
    public ResponseEntity<CreateOrderResponseDto> registerOrder(@Parameter(
            description = "object to create an order",
            required = true,
            schema = @Schema(implementation = CreateOrderRequestDto.class))
                                                                @RequestBody CreateOrderRequestDto orderRequestDto, @Parameter(
            description = "The authentication token with Bearer prefix for search the id del Customer",
            required = true, schema = @Schema(type = "String", format = "jwt"))
                                                                @RequestHeader(HttpHeaders.AUTHORIZATION) String tokenWithBearerPrefix) {
        final CreateOrderResponseDto orderRegistered = this.customerService.saveOrder(orderRequestDto, tokenWithBearerPrefix);
        return new ResponseEntity<>(orderRegistered, HttpStatus.CREATED);
    }

    @Operation(summary = "cancel order in pending status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order canceled"),
            @ApiResponse(responseCode = "403", description = "Role other than customer"),
            @ApiResponse(responseCode = "404", description = "The order not exist"),
            @ApiResponse(responseCode = "404", description = "The order does not belong to the user"),
            @ApiResponse(responseCode = "409", description = "The order is in a status other than pending")
    })
    @PreAuthorize(value = "hasRole('CLIENTE')")
    @PatchMapping(value = "order/cancel/{idOrder}")
    public ResponseEntity<CanceledOrderResponseDto> cancelOrder(@Parameter(
            description = "IdOrder to cancel the order", required = true,
            schema = @Schema(implementation = Long.class))
                                                                @PathVariable(name = "idOrder") Long idOrder, @Parameter(
            description = "The authentication token with Bearer prefix for search the user customer by id",
            required = true, schema = @Schema(type = "String", format = "jwt"))
                                                                @RequestHeader(HttpHeaders.AUTHORIZATION) String tokenWithBearerPrefix) {
        final CanceledOrderResponseDto orderCanceled = this.customerService.cancelOrder(idOrder, tokenWithBearerPrefix);
        return new ResponseEntity<>(orderCanceled, HttpStatus.OK);
    }

    @Operation(summary = "Add single dish order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registered single dish order"),
            @ApiResponse(responseCode = "403", description = "Role other than customer", content = @Content),
            @ApiResponse(responseCode = "404", description = "The dish not exist", content = @Content),
            @ApiResponse(responseCode = "404", description = "The restaurant not exist", content = @Content),
            @ApiResponse(responseCode = "404", description = "Dish type meat grams its range is different between 250 to 750", content = @Content)
    })
    @PostMapping(value = "{idRestaurant}/add-order")
    @PreAuthorize(value = "hasRole('CLIENTE')")
    public ResponseEntity<SingleDishOrderResponseDto> addSingleDishOrder(@Parameter(
            description = "Object to add details of an order as a customer",required = true,
            schema = @Schema(implementation = OrderWithASingleDishDto.class))
                                                                         @RequestBody OrderWithASingleDishDto orderWithASingleDishDto,
                                                                         @PathVariable(name = "idRestaurant") Long idRestaurant) {
        final SingleDishOrderResponseDto registeredSingleDishOrder = this.customerService.addSingleDishOrder(orderWithASingleDishDto, idRestaurant);
        return new ResponseEntity<>(registeredSingleDishOrder, HttpStatus.CREATED);
    }
}
