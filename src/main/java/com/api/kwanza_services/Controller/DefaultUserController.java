package com.api.kwanza_services.Controller;

import com.api.kwanza_services.Config.jwt.JwtPrincipal;
import com.api.kwanza_services.Dto.User.DefaultUser.AlterarPasswordDto;
import com.api.kwanza_services.Dto.User.DefaultUser.AtualizarUtilizadorRequestDto;
import com.api.kwanza_services.Dto.User.DefaultUser.DefaultUserFiltro;
import com.api.kwanza_services.Dto.User.DefaultUser.DefaultUserResponseDto;
import com.api.kwanza_services.Service.User.DefaultUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class DefaultUserController {

    private final DefaultUserService defaultUserService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<DefaultUserResponseDto> listar(
        @ModelAttribute DefaultUserFiltro filtro,
        @PageableDefault(size = 20) Pageable pageable
    ) {
        return defaultUserService.getAllUsers(filtro, pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public DefaultUserResponseDto buscar(@PathVariable UUID id) {
        return defaultUserService.buscarPorId(id);
    }


    @PutMapping("/{id}")
    public DefaultUserResponseDto actualizar(
        @PathVariable UUID id,
        @Valid @RequestBody AtualizarUtilizadorRequestDto dto,
        @AuthenticationPrincipal JwtPrincipal principal
    ) {
        exigirProprioOuAdmin(principal, id);
        return defaultUserService.updatePerfil(id, dto);
    }

    /**
     * mudar a palavra-passe do utilizador.
     */
    @PatchMapping("/me/password")
    public Map<String, String> alterarPassword(
        @Valid @RequestBody AlterarPasswordDto dto,
        @AuthenticationPrincipal JwtPrincipal principal
    ) {
        String mensagem = defaultUserService.AlterPassword(dto, principal.userId());
        return Map.of("message", mensagem);
    }

// Eliminar Desactivar o utilizador
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void eliminar(@PathVariable UUID id) {
        defaultUserService.DeleteUser(id);
    }

    private void exigirProprioOuAdmin(JwtPrincipal principal, UUID id) {
        boolean isAdmin = principal != null
            && principal.roles() != null
            && principal.roles().contains("ROLE_ADMIN");

        if (!isAdmin && (principal == null || !id.equals(principal.userId()))) {
            throw new AccessDeniedException("Sem permissão para alterar outro utilizador");
        }
    }
}
