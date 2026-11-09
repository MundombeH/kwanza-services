package com.api.kwanza_services.Mapper.User;

import com.api.kwanza_services.Dto.DefaultDto;
import com.api.kwanza_services.Dto.Roles_Permissions.Permissions.PermissionResumoDto;
import com.api.kwanza_services.Dto.Roles_Permissions.Roles.RolesResumoDto;
import com.api.kwanza_services.Dto.User.DefaultUser.DefaultUserResponseDto;
import com.api.kwanza_services.Model.DefaultModel;
import com.api.kwanza_services.Model.Roles_Permisisons.PermissionModel;
import com.api.kwanza_services.Model.Roles_Permisisons.RoleModel;
import com.api.kwanza_services.Model.User.DefaultUserModel;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DefaultUserMapper {


    public DefaultUserResponseDto response(DefaultUserModel model)

    {
        DefaultUserResponseDto dto = new DefaultUserResponseDto();


        fillDefaultFields(dto, model) ;

        dto.setEmail(model.getEmail());
        dto.setNome(model.getNome());
        dto.setNumeroDeTelefone(model.getNumeroDeTelefone());
        dto.setPerfilAtivo(model.getPerfilAtivo());
        dto.setId(model.getId());


        dto.setRoles(mapRoles(model.getRoles()));

        return dto;

    };


    private Set<RolesResumoDto> mapRoles(Set<RoleModel> roles) {
        if (roles == null) {
            return Set.of();
        }

        return roles.stream()
                .map(this::toRoleResumo)
                .collect(Collectors.toSet());
    }

    private RolesResumoDto toRoleResumo(RoleModel model) {
        RolesResumoDto dto = new RolesResumoDto();
        dto.setId(model.getId());
        dto.setDescricao(model.getDescricao());
        dto.setIsActive(model.getIsActive());
        dto.setPermissions(mapPermissions(model.getPermissions()));
        return dto;
    }

    private Set<PermissionResumoDto> mapPermissions(Set<PermissionModel> permissions) {
        if (permissions == null) {
            return Set.of();
        }

        return permissions.stream()
                .map(this::toPermissionResumo)
                .collect(Collectors.toSet());
    }

    private PermissionResumoDto toPermissionResumo(PermissionModel model) {
        PermissionResumoDto dto = new PermissionResumoDto();
        dto.setId(model.getId());
        dto.setDescricao(model.getDescricao());
        dto.setIsActive(model.getIsActive());
        return dto;
    }


    private void fillDefaultFields(DefaultDto dto, DefaultModel model) {
        dto.setId(model.getId());
        dto.setCreatedAt(model.getCreatedAt());
        dto.setUpdatedAt(model.getUpdatedAt());
        dto.setIsActive(model.getIsActive());
    }
}
