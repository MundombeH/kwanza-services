package com.api.kwanza_services.Dto.Localizacao.Provincia;

import java.util.UUID;

import com.api.kwanza_services.Dto.Localizacao.Pais.PaisResumoDTO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProvinciasResumoDTO {

    private UUID id;
    private String descricao;
    private PaisResumoDTO pais;
    private boolean isActive;

}
