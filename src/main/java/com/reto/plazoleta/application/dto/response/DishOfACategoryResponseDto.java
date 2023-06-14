package com.reto.plazoleta.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DishOfACategoryResponseDto {

    private Long idCategory;
    private String name;
    List<ResponseMenuDto> dishes;
}
