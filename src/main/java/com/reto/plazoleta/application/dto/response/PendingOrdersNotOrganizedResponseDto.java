package com.reto.plazoleta.application.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PendingOrdersNotOrganizedResponseDto {

    private Long idOrder;
    private Long idUserCustomer;
    private LocalDate date;
    private String status;
    private List<PendingDishResponseDto> dishes;
}
