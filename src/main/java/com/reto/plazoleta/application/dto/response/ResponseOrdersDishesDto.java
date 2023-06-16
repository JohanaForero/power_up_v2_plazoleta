package com.reto.plazoleta.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseOrdersDishesDto {

    private Long idDish;
    private String name;
    private String imageDish;
    private String descriptionDish;
}
