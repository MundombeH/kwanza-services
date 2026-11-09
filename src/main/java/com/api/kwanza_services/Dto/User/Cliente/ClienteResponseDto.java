package com.api.kwanza_services.Dto.User.Cliente;

import com.api.kwanza_services.Dto.DefaultDto;
import com.api.kwanza_services.Dto.Localizacao.Bairro.BairroResumoDto;
import com.api.kwanza_services.Dto.User.DefaultUser.DefaultUserResponseDto;
import com.api.kwanza_services.Enums.SexoEnum;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClienteResponseDto extends DefaultDto {

    private DefaultUserResponseDto usuario;
    private SexoEnum sexo;
    private BairroResumoDto bairro;
}
