package com.api.kwanza_services.Dto.Localizacao.Municipio;


import com.api.kwanza_services.Dto.Localizacao.Provincia.ProvinciasResumoDTO;
import com.api.kwanza_services.Dto.DefaultDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MunicipiosResponseDTO extends DefaultDto {


    private String descricao;
    private ProvinciasResumoDTO provinciaId;
}
