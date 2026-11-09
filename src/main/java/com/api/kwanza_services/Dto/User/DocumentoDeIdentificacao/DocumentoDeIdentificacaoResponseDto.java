package com.api.kwanza_services.Dto.User.DocumentoDeIdentificacao;

import com.api.kwanza_services.Dto.DefaultDto;
import com.api.kwanza_services.Dto.Localizacao.Bairro.BairroResumoDto;
import com.api.kwanza_services.Dto.Localizacao.Provincia.ProvinciasResumoDTO;
import com.api.kwanza_services.Enums.EstadoCivilEnum;
import com.api.kwanza_services.Enums.SexoEnum;
import com.api.kwanza_services.Enums.TipoDocumentoEnum;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
public class DocumentoDeIdentificacaoResponseDto extends DefaultDto {

    private String nomeCompleto;
    private String numeroDoBi;
    private String nomeDoPai;
    private String nomeDaMae;
    private String residencia;
    private String naturalDe;
    private String bairroDe;
    private String provinciaDe;
    private LocalDate dataDeNascimento;
    private SexoEnum sexo;
    private EstadoCivilEnum estadoCivil;
    private LocalDate emitidoEm;
    private LocalDate validoAte;
    private BigDecimal altura;
    private TipoDocumentoEnum tipoDocumento;
    private String codigoDoPais;
    private String profissao;
    private String numeroDoPassaporte;
    private String numeroPessoal;
}
