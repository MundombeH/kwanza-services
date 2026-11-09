package com.api.kwanza_services.Dto.Roles_Permissions.Permissions;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class PermissionResumoDto {
    private UUID id;
    private String descricao;
    private Boolean isActive;

}
