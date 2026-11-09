package com.api.kwanza_services.Dto.User.Cliente;

import com.api.kwanza_services.Dto.User.DefaultUser.DefaultUserRequestDto;
import com.api.kwanza_services.Enums.SexoEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Setter
@Getter
public class RegistoClienteRequestDto extends DefaultUserRequestDto {

    @NotNull(message = "O sexo é obrigatório")
    private SexoEnum sexo;

    @NotNull(message = "O bairro é obrigatório")
    private UUID bairroId;
}
