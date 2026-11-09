package com.api.kwanza_services.Dto.Roles_Permissions.Roles;

import com.api.kwanza_services.Dto.Roles_Permissions.Permissions.PermissionResumoDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Setter
@Getter
public class RolesResumoDto {
    private  String descricao;
    private UUID id;
    private Set<PermissionResumoDto> permissions;
    private Boolean isActive;
}
