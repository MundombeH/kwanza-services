package com.api.kwanza_services.Dto.Localizacao.Provincia;


import java.util.UUID;

import com.api.kwanza_services.Dto.Localizacao.Pais.PaisResumoDTO;
import com.api.kwanza_services.Dto.DefaultDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProvinciasResponseDTO extends DefaultDto {

    private UUID id;
    private String descricao;
    private PaisResumoDTO pais;

    // private Set<MunicipiosResumoDTO> municipios = new HashSet<>();
}
