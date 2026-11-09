package com.api.kwanza_services.Dto.Roles_Permissions.Permissions;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Setter
@Getter
public class PermissionsRequestDto {
    private UUID id;

    @NotBlank(message = "A descricao da permissão não pode estar em branco")
    @NotNull(message = "A descricao da permissão não pode ser nula")
    @Size(min = 3, message = "A descricao da permissão deve ter no mínimo 3 caracteres")
    private  String descricao;

}
