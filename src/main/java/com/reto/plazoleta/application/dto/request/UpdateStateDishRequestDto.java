package com.reto.plazoleta.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateStateDishRequestDto {

    private Long idDish;
    private Long idRestaurant;
    private Boolean stateDish;
}
