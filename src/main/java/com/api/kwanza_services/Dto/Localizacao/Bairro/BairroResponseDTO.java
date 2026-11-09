package com.api.kwanza_services.Dto.Localizacao.Bairro;


import com.api.kwanza_services.Dto.DefaultDto;
import com.api.kwanza_services.Dto.Localizacao.Municipio.MunicipiosResumoDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BairroResponseDTO extends DefaultDto {


    private MunicipiosResumoDTO municipio;
    private String descricao;
}
