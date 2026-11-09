package com.api.kwanza_services.Model.User;

import com.api.kwanza_services.Model.DefaultModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "USUARIO_GESTOR")
@Setter
@Getter
public class GestorModel extends DefaultModel {

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private DefaultUserModel usuario;

    @Column(name = "numero_do_funcionario", nullable = false, unique = true)
    private String numeroDoFuncionario;

    @Column(name = "identidade_validada")
    private Boolean identidadeValidada = false;


    @OneToOne
    @JoinColumn(name ="documento_de_identificacao")
    private DocumentoDeIdentificacaoModel documentoDeIdentificacao;
}
