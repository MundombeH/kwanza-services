package com.api.kwanza_services.Controller;

import com.api.kwanza_services.Config.jwt.JwtPrincipal;
import com.api.kwanza_services.Dto.User.Admin.AdminRequestDto;
import com.api.kwanza_services.Dto.User.Cliente.ClienteRequestDto;
import com.api.kwanza_services.Dto.User.DefaultUser.DefaultUserResponseDto;
import com.api.kwanza_services.Dto.User.Fornecedor.RegistoFornecedorRequestDto;
import com.api.kwanza_services.Dto.User.Gestor.GestorRequestDto;
import com.api.kwanza_services.Enums.TipoPerfilEnum;
import com.api.kwanza_services.Service.User.RegistoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

/**
 * Gestão dos perfis de um utilizador.

 */
@RestController
@RequestMapping("/api/perfis")
@RequiredArgsConstructor
public class PerfilController {

    private final RegistoService registoService;

    @PostMapping("/clientes")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public DefaultUserResponseDto criarPerfilCliente(@Valid @RequestBody ClienteRequestDto dto) {
        return registoService.adicionarPerfilCliente(dto);
    }

    @PostMapping("/admins")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public DefaultUserResponseDto criarPerfilAdmin(@Valid @RequestBody AdminRequestDto dto) {
        return registoService.adicionarPerfilAdmin(dto);
    }

    @PostMapping("/fornecedores")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public DefaultUserResponseDto criarPerfilFornecedor(@Valid @RequestBody RegistoFornecedorRequestDto dto) {
        return registoService.adicionarPerfilFornecedor(dto);
    }

    @PostMapping("/gestores")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public DefaultUserResponseDto criarPerfilGestor(@Valid @RequestBody GestorRequestDto dto) {
        return registoService.adicionarPerfilGestor(dto);
    }

    /**
     * Troca o perfil activo. O próprio utilizador ou um administrador.
     */
    @PutMapping("/usuarios/{usuarioId}/ativo/{tipo}")
    public DefaultUserResponseDto trocarPerfil(
        @PathVariable UUID usuarioId,
        @PathVariable TipoPerfilEnum tipo,
        @AuthenticationPrincipal JwtPrincipal principal
    ) {
        exigirProprioOuAdmin(principal, usuarioId);
        return registoService.trocarPerfil(usuarioId, tipo);
    }

    @GetMapping("/usuarios/{usuarioId}/{tipo}")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Object> temRegistoDePerfil(
        @PathVariable UUID usuarioId,
        @PathVariable TipoPerfilEnum tipo
    ) {
        return Map.of(
            "usuarioId", usuarioId,
            "perfil", tipo,
            "existe", registoService.temRegistoDePerfil(usuarioId, tipo)
        );
    }

    private void exigirProprioOuAdmin(JwtPrincipal principal, UUID usuarioId) {
        boolean isAdmin = principal != null
            && principal.roles() != null
            && principal.roles().contains("ROLE_ADMIN");

        if (!isAdmin && (principal == null || !usuarioId.equals(principal.userId()))) {
            throw new AccessDeniedException("Sem permissão para alterar outro utilizador");
        }
    }
}
