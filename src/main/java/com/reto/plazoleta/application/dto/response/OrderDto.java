package com.reto.plazoleta.application.dto.response;

import com.reto.plazoleta.domain.model.EmployeeRestaurantModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long id;
    private Long idUserCustomer;
    private LocalDate date;
    private String status;
    private EmployeeRestaurantModel employee;
}
