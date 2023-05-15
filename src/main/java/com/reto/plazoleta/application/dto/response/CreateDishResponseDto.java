package com.reto.plazoleta.application.dto.response;

import com.reto.plazoleta.domain.model.CategoryModel;
import com.reto.plazoleta.domain.model.RestaurantModel;
import lombok.Getter;
import lombok.Setter;

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
