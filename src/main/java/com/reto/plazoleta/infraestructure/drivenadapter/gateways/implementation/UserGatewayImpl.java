package com.reto.plazoleta.infraestructure.drivenadapter.gateways.implementation;

import com.reto.plazoleta.infraestructure.configuration.security.exception.UserDoesNotExistException;
import com.reto.plazoleta.infraestructure.drivenadapter.gateways.IUserGateway;
import com.reto.plazoleta.infraestructure.drivenadapter.gateways.User;
import com.reto.plazoleta.infraestructure.exception.UserInTokenIsInvalidException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserGatewayImpl implements IUserGateway {

    private static final String BASE_URL = "http://localhost:8090/user-micro/user/";
    WebClient webClient = WebClient.builder().baseUrl(BASE_URL).build();

    @Override
    public User getUserById(Long idUser, String token) {
        return webClient.get().uri(uriBuilder -> uriBuilder.path("verifier")
                        .queryParam("iduser", idUser)
                        .build())
                .header(HttpHeaders.AUTHORIZATION, token)
                .exchangeToMono( clientResponse -> {
                    if(clientResponse.statusCode().is2xxSuccessful()) {
                        return clientResponse.bodyToMono(User.class);
                    } else if(clientResponse.statusCode().equals(HttpStatus.UNAUTHORIZED)) {
                        return Mono.error(new UserInTokenIsInvalidException("Username or role in the token is invalid"));
                    } else if (clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)) {
                        return Mono.error(new UserDoesNotExistException("User not found"));
                    } else {
                        return clientResponse.createException().flatMap(Mono::error);
                    }
                })
                .block();
    }
}
