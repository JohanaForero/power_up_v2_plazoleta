package com.reto.plazoleta.infraestructure.configuration.security.jwt.implementation;

import com.reto.plazoleta.infraestructure.configuration.security.exception.PermissionDeniedException;
import com.reto.plazoleta.infraestructure.configuration.security.exception.TokenInvalidException;
import com.reto.plazoleta.infraestructure.configuration.security.exception.UserDoesNotExistException;
import com.reto.plazoleta.infraestructure.configuration.security.jwt.IUserVerifierToken;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class UserVerifierToken implements IUserVerifierToken {

    private static final String BASE_URL = "http://localhost:8090/usuario-micro/usuario/";
    WebClient webClient = WebClient.builder().baseUrl(BASE_URL).build();

    @Override
    public void isValidTokenUser( String token) {
        webClient.get()
                .uri( uriBuilder -> uriBuilder.path("verifier").build())
                .header(HttpHeaders.AUTHORIZATION, token)
                .exchangeToMono( clientResponse  -> {
                    if(clientResponse.statusCode().is2xxSuccessful()) {
                        return  Mono.empty();
                    } else if(clientResponse.statusCode().equals(HttpStatus.UNAUTHORIZED)) {
                        return Mono.error(new TokenInvalidException("Username or role in the token is invalid"));
                    } else if (clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)) {
                        return Mono.error(new UserDoesNotExistException("User not found"));
                    } else if (clientResponse.statusCode().equals(HttpStatus.FORBIDDEN)) {
                        return Mono.error(new PermissionDeniedException("Permission denied you are not authorized to use this service"));
                    }else {
                        return clientResponse.createException().flatMap(Mono::error);
                    }
                })
                .doOnError(e -> {
                    if (e instanceof TokenInvalidException) {
                        throw (TokenInvalidException) e;
                    } else if (e instanceof UserDoesNotExistException) {
                        throw (UserDoesNotExistException) e;
                    } else if (e instanceof PermissionDeniedException) {
                        throw (PermissionDeniedException) e;
                    } else {
                        throw new RuntimeException("Unexpected error: " + e.getMessage(), e);
                    }
                })
                .subscribe();
    }
}
