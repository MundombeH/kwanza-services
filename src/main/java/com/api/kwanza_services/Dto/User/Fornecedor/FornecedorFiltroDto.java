package com.api.kwanza_services.Dto.User.Fornecedor;

import com.api.kwanza_services.Enums.SexoEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Setter
@Getter
public class FornecedorFiltroDto {

    private String q;
    private UUID bairroId;
    private SexoEnum sexo;
    private Boolean identidadeValidada;
}
