package com.reto.plazoleta.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateDishRequestDto {

    private String name;
    private Double price;
    private String descriptionDish;
    private String imageDish;
    private Long idCategory;
    private Long idRestaurant;

}
