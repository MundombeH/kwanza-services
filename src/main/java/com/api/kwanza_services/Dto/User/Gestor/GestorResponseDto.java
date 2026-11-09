package com.api.kwanza_services.Dto.User.Gestor;

import com.api.kwanza_services.Dto.DefaultDto;
import com.api.kwanza_services.Dto.User.DefaultUser.DefaultUserResponseDto;
import com.api.kwanza_services.Dto.User.DocumentoDeIdentificacao.DocumentoDeIdentificacaoResponseDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GestorResponseDto extends DefaultDto {

    private Boolean identidadeValidada;
    private DefaultUserResponseDto usuario;
    private DocumentoDeIdentificacaoResponseDto documentoDeIdentificacao;
    private String numeroDoFuncionario;
}
