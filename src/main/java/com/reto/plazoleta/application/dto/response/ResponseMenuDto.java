package com.reto.plazoleta.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMenuDto {

    private Long idDish;
    private String name;
    private String descriptionDish;
    private Double price;
    private String imageDish;
    private Boolean stateDish;
}
