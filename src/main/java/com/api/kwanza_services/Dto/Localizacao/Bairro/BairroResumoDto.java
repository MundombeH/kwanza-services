package com.api.kwanza_services.Dto.Localizacao.Bairro;

import com.api.kwanza_services.Dto.Localizacao.Municipio.MunicipiosResumoDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class BairroResumoDto {
    private UUID id;
    private String descricao;
    private Boolean isActive;
    private MunicipiosResumoDTO municipio;
}
