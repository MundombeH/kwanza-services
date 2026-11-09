package com.api.kwanza_services.Dto.Roles_Permissions.Roles;


import com.api.kwanza_services.Dto.DefaultDto;
import com.api.kwanza_services.Dto.Roles_Permissions.Permissions.PermissionsResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
public class RolesResponseDto  extends DefaultDto {
    private UUID id;
    private  String descricao;
    private Set<PermissionsResponseDto> permissions = new HashSet<>();
}
