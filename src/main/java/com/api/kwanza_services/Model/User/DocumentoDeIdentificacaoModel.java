package com.api.kwanza_services.Model.User;

import com.api.kwanza_services.Enums.EstadoCivilEnum;
import com.api.kwanza_services.Enums.SexoEnum;
import com.api.kwanza_services.Enums.TipoDocumentoEnum;
import com.api.kwanza_services.Model.DefaultModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table(name = "DOCUMENTO_DE_IDENTIFICACAO")
@Entity
@Setter
@Getter
public class DocumentoDeIdentificacaoModel extends DefaultModel {

    @Column(name = "nome_completo")
    private String nomeCompleto;

    @Column(name = "numero_do_bi")
    private String numeroDoBi;

    @Column(name = "nome_do_pai")
    private String nomeDoPai;

    @Column(name = "nome_da_mae")
    private String nomeDaMae;

    /**
     * Bairro tal como aparece no documento (texto lido por OCR).
     * Não é uma FK: o documento é uma fotografia histórica e o texto pode
     * não corresponder exactamente ao catálogo de bairros.
     */
    @Column(name = "bairro_de", length = 120)
    private String bairroDe;

    @Column(name = "residencia")
    private String residencia;

    @Column(name = "natural_de")
    private String naturalDe;

    /**
     * Província tal como aparece no documento (texto lido por OCR).
     */
    @Column(name = "provincia_de", length = 120)
    private String provinciaDe;

    @Column(name = "data_de_nascimento")
    private LocalDate dataDeNascimento;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexo")
    private SexoEnum sexo;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_civil")
    private EstadoCivilEnum estadoCivil;

    @Column(name = "emitido_em")
    private LocalDate emitidoEm;

    @Column(name = "valido_ate")
    private LocalDate validoAte;

    @Column(name = "altura")
    private BigDecimal altura;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento")
    private TipoDocumentoEnum tipoDocumento;

    @Column(name = "codigo_do_pais")
    private String codigoDoPais;

    @Column(name = "profissao")
    private String profissao;

    @Column(name = "numero_do_passaporte")
    private String numeroDoPassaporte;

    @Column(name = "numero_pessoal")
    private String numeroPessoal;
}
