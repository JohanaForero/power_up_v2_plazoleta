package com.reto.plazoleta.domain.spi;

public interface IToken {

    String getEmailFromToken(String tokenWithPrefixBearer);

    String getTokenWithPrefixBearerFromUserAuthenticated();
}
