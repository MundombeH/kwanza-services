package com.api.kwanza_services.Model.Localizacao;

import com.api.kwanza_services.Model.DefaultModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "BAIRROS")
@Setter
@Getter
public class BairroModel extends DefaultModel {

    @Column(name ="descricao", nullable = false)
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "municipio_id", nullable = false)
    @JsonBackReference
    private MunicipioModel municipio;
}
