package com.api.kwanza_services.Dto.User.Cliente;

import com.api.kwanza_services.Enums.SexoEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class ClienteRequestDto {

    @NotNull(message = "O Usuário é obrigatório")
    private UUID usuario;

    @NotNull(message = "O sexo não pode ser vazio")
    private SexoEnum sexo;

    @NotNull(message = "O bairro é obrigatório")
    private UUID bairro;
}
