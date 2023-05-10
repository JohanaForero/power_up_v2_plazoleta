package com.reto.plazoleta.application.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDishRequestDto {
    private String name;
    private String descriptionDish;
    private Double price;
    private String imageDish;
    private String stateDish;
    private Long restaurantId;
}
