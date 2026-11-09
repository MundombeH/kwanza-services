package com.api.kwanza_services.Model.Localizacao;

import com.api.kwanza_services.Model.DefaultModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PROVINCIAS")
@Setter
@Getter
public class ProvinciasModel extends DefaultModel {


    private String descricao;
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "pais_id")
    private PaisModel pais;


    @JsonManagedReference // O Pai gerencia os municípios
    @OneToMany(mappedBy = "provincia")
    private Set<MunicipioModel> municipios = new HashSet<>();
}
