package com.reto.plazoleta.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateDishResponseDto {

    private String name;
    private Double price;
    private String descriptionDish;
    private String imageDish;
    private Boolean stateDish;
    private String nameRestaurant;
    private String nameCategory;

}
