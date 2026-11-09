package com.api.kwanza_services.Dto.Localizacao.Bairro;

import java.util.UUID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BairroRequestDTO {

    @NotBlank
    private String descricao;

    @NotNull
    private UUID municipioId;
}
