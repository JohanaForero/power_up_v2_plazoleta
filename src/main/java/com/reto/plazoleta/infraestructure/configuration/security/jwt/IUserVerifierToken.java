package com.reto.plazoleta.infraestructure.configuration.security.jwt;

public interface IUserVerifierToken {

    void isValidTokenUser(String token);
}
