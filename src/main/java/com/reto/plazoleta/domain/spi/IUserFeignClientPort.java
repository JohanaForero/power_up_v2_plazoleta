package com.reto.plazoleta.domain.spi;

import com.reto.plazoleta.domain.model.UserModel;

public interface IUserFeignClientPort {

    Boolean existsUserById(Long usuarioId);

    UserModel getUserById(Long usuarioId);

    UserModel getUserByCorreo(String correo);
}
