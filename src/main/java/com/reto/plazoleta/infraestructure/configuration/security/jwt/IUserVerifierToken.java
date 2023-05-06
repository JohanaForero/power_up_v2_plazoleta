package com.reto.plazoleta.infraestructure.configuration.security.jwt;

public interface IUserVerifierToken {

    Boolean isValidTokenUser(String token);
}
