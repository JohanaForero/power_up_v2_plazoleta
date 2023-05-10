package com.reto.plazoleta.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateDishStateRequestDto {
    private Long idDish;
    private String stateDish;
    private Long idRestaurant;
}
