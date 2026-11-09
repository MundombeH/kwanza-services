package com.api.kwanza_services.Dto.User.DefaultUser;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class AtualizarUtilizadorRequestDto {

    @NotBlank(message = "O Nome completo é obrigatório")
    private String nome;

    @Email(message = "Formato de e-mail errado")
    @NotBlank(message = "O E-mail é obrigatório")
    private String email;

    @NotBlank(message = "O número de telefone é obrigatório")
    private String numeroDeTelefone;
}
