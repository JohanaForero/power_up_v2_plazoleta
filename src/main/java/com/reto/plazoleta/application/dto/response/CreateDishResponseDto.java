package com.reto.plazoleta.application.dto.response;

import com.reto.plazoleta.domain.model.CategoryModel;
import com.reto.plazoleta.domain.model.RestaurantModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDishResponseDto {

    private String nameDish;
    private Double price;
    private String descriptionDish;
    private String imageDish;
    private CategoryModel nameCategory;
    private RestaurantModel nameRestaurant;
    private Boolean stateDish;

}
