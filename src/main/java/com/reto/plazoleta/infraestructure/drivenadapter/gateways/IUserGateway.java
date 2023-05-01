package com.reto.plazoleta.infraestructure.drivenadapter.gateways;

public interface IUserGateway {

    User getUserById(Long idUser, String token);
}
