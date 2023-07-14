package com.reto.plazoleta.application.dto.response;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PendingDishResponseDto {

    private Long idDish;
    private String typeDish;
    private String typeDessert;
    private String accompaniment;
    private String flavor;
    private Integer grams;
}
