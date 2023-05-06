package com.reto.plazoleta.infraestructure.configuration.security.jwt.implementation;

import com.reto.plazoleta.infraestructure.configuration.security.jwt.IUserVerifierToken;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class UserVerifierToken implements IUserVerifierToken {

    private static final String BASE_URL = "http://localhost:8090/user-micro/user/";
    WebClient webClient = WebClient.builder().baseUrl(BASE_URL).build();

    @Override
    public Boolean isValidTokenUser( String token) {
        try {
            webClient.get()
                    .uri("verifier")
                    .header(HttpHeaders.AUTHORIZATION, token);
            return true;
        } catch(Exception e) {
            return false;
        }
    }
}
