package com.reto.plazoleta.application.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDishRequestDto {

    private String nameDish;
    private Double price;
    private String descriptionDish;
    private String imageDish;
    private Long idCategory;
    private Long idRestaurant;
    private Boolean stateDish;

}
