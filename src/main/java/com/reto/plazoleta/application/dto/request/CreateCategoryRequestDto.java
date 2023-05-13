package com.reto.plazoleta.application.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCategoryRequestDto {
    private Long idCategory;
    private String name;
    private String description;
}
