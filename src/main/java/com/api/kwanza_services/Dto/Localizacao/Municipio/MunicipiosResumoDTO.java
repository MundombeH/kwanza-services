package com.api.kwanza_services.Dto.Localizacao.Municipio;

import java.util.UUID;

import com.api.kwanza_services.Dto.Localizacao.Provincia.ProvinciasResumoDTO;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter

public class MunicipiosResumoDTO {

    private UUID id;
    private String descricao;
    private boolean isActive;
    private ProvinciasResumoDTO provincia;

}
