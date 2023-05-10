package com.reto.plazoleta.application.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDishResponseDto {
    private String name;
    private String descriptionDish;
    private Double price;
    private String imageDish;
    private String stateDish;
    private Long restaurantId;
}
