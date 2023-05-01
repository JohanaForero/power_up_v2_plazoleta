package com.reto.plazoleta.application.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestToCreateRestaurantDto {

    private String name;
    private String address;
    private String phone;
    private String urlLogo;
    private Long  nit;
    private Long idOwner;
}
