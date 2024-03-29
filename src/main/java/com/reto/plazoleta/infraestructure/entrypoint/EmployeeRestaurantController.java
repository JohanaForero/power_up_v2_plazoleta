package com.reto.plazoleta.infraestructure.entrypoint;

import com.reto.plazoleta.application.dto.response.*;
import com.reto.plazoleta.application.handler.IEmployeeService;
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

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/micro-small-square/")
public class EmployeeRestaurantController {

    private static final String STATUS_DEFAULT = "PENDIENTE";
    private final IEmployeeService employeeRestaurantService;

    @Operation(summary = "List orders paginated by the field status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of orders filtered by status"),
            @ApiResponse(responseCode = "204", description = "No order found"),
            @ApiResponse(responseCode = "403", description = "Role other than employee"),
            @ApiResponse(responseCode = "404", description = "The Restaurant not exist")
    })
    @PreAuthorize(value = "hasRole('EMPLEADO')")
    @GetMapping(value = "orders")
    public ResponseEntity<Page<ResponseOrdersPaginatedDto>> getAllOrdersFilterByStatus(
            @Parameter( description = "Number of orders by page", schema = @Schema(implementation = Integer.class))
            @RequestParam(name = "sizeItems", defaultValue = "10") Integer sizeItems,
            @Parameter( description = "Number of the page in the pagination of the orders", schema = @Schema(implementation = Integer.class))
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @Parameter( description = "order status name", schema = @Schema(implementation = String.class))
            @RequestParam(name = "status", defaultValue = STATUS_DEFAULT) String status,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String tokenWithPrefixBearer) {
        final Page<ResponseOrdersPaginatedDto> ordersPaginated = this.employeeRestaurantService.getAllOrdersFilterByStatus(sizeItems, pageNumber,  status, tokenWithPrefixBearer);
        return ResponseEntity.ok(ordersPaginated);
    }

    @Operation(summary = "The employee will be assigned to one or more orders at the same time and the order was changed to pending status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The employee was assigned to one or more orders successfully"),
            @ApiResponse(responseCode = "403", description = "Role other than employee"),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "404", description = "The restaurant that the employee belongs to does not exist"),
            @ApiResponse(responseCode = "409", description = "The order is already in process with another employee")
    })
    @PatchMapping(value = "restaurant/order")
    @PreAuthorize(value = "hasRole('EMPLEADO')")
    public ResponseEntity<List<AssignedOrdersResponseDto>> assignEmployeeToOrderAndChangeStatusToInPreparation(
            @Parameter( description = "List of order identifiers to be assigned to the employee", schema = @Schema(implementation = List.class))
            @RequestParam(name = "idOrder") List<Long> idOrders,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String tokenWithPrefixBearer) {
        return new ResponseEntity<>(this.employeeRestaurantService
                .assignOrderAndChangeStatusToInPreparation(idOrders, tokenWithPrefixBearer), HttpStatus.OK);
    }

    @Operation(summary = "Change order status to delivered")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated order with delivered status"),
            @ApiResponse(responseCode = "403", description = "Role other than employee"),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "404", description = "The restaurant that the employee belongs to does not exist"),
            @ApiResponse(responseCode = "409", description = "The order has a status other than ready")
    })
    @PatchMapping(value = "restaurant/order/status/delivered/{pin}")
    @PreAuthorize(value = "hasRole('EMPLEADO')")
    public ResponseEntity<OrderDeliveredResponseDto> changeOrderStatusToDelivered(@Parameter( description = "numeric value where the order identifier was encrypted",
            schema = @Schema(implementation = Long.class)) @PathVariable(name = "pin") Long orderPin, @Parameter(
            description = "Token to validate if the employee belongs to the restaurant of the order in which he works",
            required = true, schema = @Schema(type = "String", format = "jwt"))
                                                                                  @RequestHeader(HttpHeaders.AUTHORIZATION) String tokenWithPrefixBearer ) {
        return ResponseEntity.ok(this.employeeRestaurantService.changeOrderStatusToDelivered(orderPin, tokenWithPrefixBearer));
    }

    @Operation(summary = "The employee takes an order by priority in pending status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order taken by priority and removed from the list"),
            @ApiResponse(responseCode = "204", description = "No order found", content = @Content),
            @ApiResponse(responseCode = "403", description = "Role other than employee", content = @Content)
    })
    @GetMapping(value = "take-order")
    @PreAuthorize(value = "hasRole('EMPLEADO')")
    public ResponseEntity<ResponseOrderDto> takeOrder() {
        final ResponseOrderDto orderTakenWithHigherPriority = this.employeeRestaurantService.getOrderByPriority();
        return ResponseEntity.ok(orderTakenWithHigherPriority);
    }

    @Operation(summary = "Pending orders not arranged by priority")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pending orders sorted by low priority"),
            @ApiResponse(responseCode = "204", description = "empty list", content = @Content),
            @ApiResponse(responseCode = "403", description = "Role other than employee", content = @Content)
    })
    @GetMapping(value = "pending-orders")
    @PreAuthorize(value = "hasRole('EMPLEADO')")
    public ResponseEntity<List<PendingOrdersNotOrganizedResponseDto>> pendingOrders() {
        return ResponseEntity.ok(this.employeeRestaurantService.pendingOrdersWithLowPriority());
    }
}
