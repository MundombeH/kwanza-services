package com.api.kwanza_services.Model.User;

import com.api.kwanza_services.Enums.SexoEnum;
import com.api.kwanza_services.Model.DefaultModel;
import com.api.kwanza_services.Model.Localizacao.BairroModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name ="USUARIO_FORNECEDOR")
@Setter
@Getter
public class FornecedorModel extends DefaultModel {


    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private DefaultUserModel usuario;

    @ManyToOne
    @JoinColumn(name = "bairro_id")
    private BairroModel bairro;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexo", nullable = false)
    private SexoEnum sexo;

    @Column(name = "identidade_validada")
    private Boolean identidadeValidada = false;

    @OneToOne
    @JoinColumn(name ="documento_de_identificacao")
    private DocumentoDeIdentificacaoModel documentoDeIdentificacao;


}
