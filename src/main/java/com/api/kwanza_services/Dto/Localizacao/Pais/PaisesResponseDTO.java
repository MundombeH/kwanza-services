package com.api.kwanza_services.Dto.Localizacao.Pais;

import java.util.UUID;


import com.api.kwanza_services.Dto.DefaultDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PaisesResponseDTO extends DefaultDto {


    private UUID id;
    private String descricao;

}
