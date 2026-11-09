package com.api.kwanza_services.Dto.User.Fornecedor;

import com.api.kwanza_services.Dto.DefaultDto;
import com.api.kwanza_services.Dto.Localizacao.Bairro.BairroResumoDto;
import com.api.kwanza_services.Dto.User.DefaultUser.DefaultUserResponseDto;
import com.api.kwanza_services.Dto.User.DocumentoDeIdentificacao.DocumentoDeIdentificacaoResponseDto;
import com.api.kwanza_services.Enums.SexoEnum;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FornecedorResponseDto extends DefaultDto {

    private DefaultUserResponseDto usuario;
    private BairroResumoDto bairro;
    private SexoEnum sexo;
    private Boolean identidadeValidada;
    private DocumentoDeIdentificacaoResponseDto documentoDeIdentificacao;
}
