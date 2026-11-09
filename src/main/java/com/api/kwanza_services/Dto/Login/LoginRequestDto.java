package com.api.kwanza_services.Dto.Login;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequestDto {

    @NotBlank(message = "O login (email ou telefone) é obrigatório")
    private String login;

    @NotBlank(message = "A palavra-passe é obrigatória")
    private String password;
}
