package com.reto.plazoleta.application.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SingleDishOrderRequestDto {

    private Long idDish;
    private String typeDish;
    private String typeDessert;
    private String accompaniment;
    private String flavor;
    private Integer grams;
}
