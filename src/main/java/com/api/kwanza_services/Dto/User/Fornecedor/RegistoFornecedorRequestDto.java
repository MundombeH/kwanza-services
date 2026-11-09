package com.api.kwanza_services.Dto.User.Fornecedor;

import com.api.kwanza_services.Dto.User.DocumentoDeIdentificacao.DocumentoDeIdentificacaoRequestDto;
import com.api.kwanza_services.Enums.SexoEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Adiciona o perfil de fornecedor a um utilizador já existente.
 * O registo é feito por um ADMIN, não é público.
 */
@Setter
@Getter
public class RegistoFornecedorRequestDto {

    @NotNull(message = "O utilizador é obrigatório")
    private UUID usuarioId;

    @NotNull(message = "O sexo é obrigatório")
    private SexoEnum sexo;

    @NotNull(message = "O bairro é obrigatório")
    private UUID bairroId;

    @Valid
    @NotNull(message = "O documento de identificação é obrigatório")
    private DocumentoDeIdentificacaoRequestDto documentoDeIdentificacao;
}
