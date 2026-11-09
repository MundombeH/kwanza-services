package com.api.kwanza_services.Dto.User.Gestor;

import com.api.kwanza_services.Dto.User.DocumentoDeIdentificacao.DocumentoDeIdentificacaoRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class GestorRequestDto {

    @NotNull(message = "O Usuário é obrigatório")
    private UUID usuario;

    @NotBlank(message = "O número do funcionário é obrigatório")
    private String numeroDoFuncionario;

    @Valid
    @NotNull(message = "O documento de identificação é obrigatório")
    private DocumentoDeIdentificacaoRequestDto documentoDeIdentificacao;
}
