package com.api.kwanza_services.Dto.User.DocumentoDeIdentificacao;

import com.api.kwanza_services.Enums.EstadoCivilEnum;
import com.api.kwanza_services.Enums.SexoEnum;
import com.api.kwanza_services.Enums.TipoDocumentoEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
public class DocumentoDeIdentificacaoRequestDto {

    @NotBlank(message = "O nome completo é obrigatório")
    private String nomeCompleto;

    private String numeroDoBi;

    private String nomeDoPai;
    private String nomeDaMae;

    @Size(max = 120, message = "O bairro não pode ter mais de 120 caracteres")
    private String bairroDe;

    private String residencia;
    private String naturalDe;

    @Size(max = 120, message = "A província não pode ter mais de 120 caracteres")
    private String provinciaDe;

    @NotNull(message = "A data de nascimento é obrigatória")
    private LocalDate dataDeNascimento;

    @NotNull(message = "O sexo é obrigatório")
    private SexoEnum sexo;

    private EstadoCivilEnum estadoCivil;
    private LocalDate emitidoEm;
    private LocalDate validoAte;
    private BigDecimal altura;

    @NotNull(message = "O tipo de documento é obrigatório")
    private TipoDocumentoEnum tipoDocumento;

    private String codigoDoPais;
    private String profissao;
    private String numeroDoPassaporte;
    private String numeroPessoal;





}
