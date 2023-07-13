package com.reto.plazoleta.application.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseOrderDto {

    private Long idOrder;
    private Long idUserCustomer;
    private LocalDate date;
    private String status;
    private Long idChef;
    private List<ResponseTypeDishOrderDto> dishes;
}