package com.api.kwanza_services.Dto.User.DefaultUser;

import com.api.kwanza_services.Dto.DefaultDto;
import com.api.kwanza_services.Dto.Roles_Permissions.Roles.RolesResumoDto;
import com.api.kwanza_services.Enums.TipoPerfilEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
public class DefaultUserResponseDto extends DefaultDto {

    private String nome;
    private String numeroDeTelefone;
    private String email;
    private TipoPerfilEnum perfilAtivo;
    private Set<RolesResumoDto> roles = new HashSet<>();
}
