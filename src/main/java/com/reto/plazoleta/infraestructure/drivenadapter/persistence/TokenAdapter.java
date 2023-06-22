package com.reto.plazoleta.infraestructure.drivenadapter.persistence;


import com.reto.plazoleta.domain.spi.IToken;
import com.reto.plazoleta.infraestructure.configuration.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TokenAdapter implements IToken {

    private final JwtProvider jwtProvider;
    @Override
    public String getEmailFromToken(String tokenWithPrefixBearer) {
        String tokenWithoutPrefix = tokenWithPrefixBearer.replace("Bearer ", "").trim();
        return jwtProvider.getAuthentication(tokenWithoutPrefix).getName();
    }
}
