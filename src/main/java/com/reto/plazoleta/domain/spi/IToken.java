package com.reto.plazoleta.domain.spi;

public interface IToken {

    String getBearerToken();

    String getCorreo(String token);

    Long getUsuarioAutenticadoId(String token);
}
