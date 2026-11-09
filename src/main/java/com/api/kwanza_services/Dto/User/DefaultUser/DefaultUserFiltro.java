package com.api.kwanza_services.Dto.User.DefaultUser;

import com.api.kwanza_services.Enums.TipoPerfilEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class DefaultUserFiltro {

    private UUID bairroId;
    private TipoPerfilEnum tipoPerfil;
}
