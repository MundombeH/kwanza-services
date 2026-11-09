package com.api.kwanza_services.Dto.Localizacao.Pais;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaisResumoDTO {

    private UUID id;
    private String descricao;
    private boolean isActive;

}
