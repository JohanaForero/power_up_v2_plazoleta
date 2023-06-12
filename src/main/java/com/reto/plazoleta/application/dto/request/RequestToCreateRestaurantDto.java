package com.reto.plazoleta.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestToCreateRestaurantDto {

    private String name;
    private String address;
    private String phone;
    private String urlLogo;
    private Long  nit;
    private Long idOwner;
}
