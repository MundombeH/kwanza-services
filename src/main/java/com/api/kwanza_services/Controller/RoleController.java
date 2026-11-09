package com.api.kwanza_services.Controller;

import com.api.kwanza_services.Enums.TipoPerfilEnum;
import com.api.kwanza_services.Service.Roles_Permisisons.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

/**
 * Gestão de roles de um utilizador. Apenas administradores.
 */
@RestController
@RequestMapping("/api/users/{usuarioId}/roles")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/{tipo}")
    public Map<String, Object> verificar(
        @PathVariable UUID usuarioId,
        @PathVariable TipoPerfilEnum tipo
    ) {
        return Map.of(
            "usuarioId", usuarioId,
            "role", tipo.getRole(),
            "atribuida", roleService.temRole(usuarioId, tipo)
        );
    }

    @PostMapping("/{tipo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atribuir(
        @PathVariable UUID usuarioId,
        @PathVariable TipoPerfilEnum tipo
    ) {
        roleService.atribuirRole(usuarioId, tipo);
    }

    @DeleteMapping("/{tipo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(
        @PathVariable UUID usuarioId,
        @PathVariable TipoPerfilEnum tipo
    ) {
        roleService.removerRole(usuarioId, tipo);
    }
}
