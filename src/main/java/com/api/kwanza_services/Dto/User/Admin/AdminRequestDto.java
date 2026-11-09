package com.api.kwanza_services.Dto.User.Admin;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class AdminRequestDto {

    @NotNull(message = "O Usuário é obrigatório")
    private UUID usuario;
}
