package com.reto.plazoleta.application.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseTypeDishOrderDto {

    private Long idDish;
    private String typeDish;
    private String accompaniment;
    private String dessertType;
    private String Flavor;
    private Integer grams;
}
