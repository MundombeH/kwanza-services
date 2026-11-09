package com.api.kwanza_services.Dto.User.Fornecedor;

import com.api.kwanza_services.Dto.User.DocumentoDeIdentificacao.DocumentoDeIdentificacaoRequestDto;
import com.api.kwanza_services.Enums.SexoEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class FornecedorRequestDto {

    @NotNull(message = "O Usuário é obrigatório")
    private UUID usuario;

    @NotNull(message = "O Bairro é obrigatório")
    private UUID bairro;

    @NotNull(message = "O Sexo não pode ser vazio")
    private SexoEnum sexo;

    @Valid
    private UUID documentoDeIdentificacao;
}
