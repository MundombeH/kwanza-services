package com.api.kwanza_services.Controller;

import com.api.kwanza_services.Dto.User.Fornecedor.FornecedorFiltroDto;
import com.api.kwanza_services.Dto.User.Fornecedor.FornecedorResponseDto;
import com.api.kwanza_services.Service.User.FornecedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/fornecedores")
@RequiredArgsConstructor
public class FornecedorController {

    private final FornecedorService fornecedorService;

    @GetMapping
    public Page<FornecedorResponseDto> pesquisar(
        @ModelAttribute FornecedorFiltroDto filtro,
        @PageableDefault(size = 20) Pageable pageable
    ) {
        return fornecedorService.pesquisar(filtro, pageable);
    }

    // Eliminar Desactivar o perfil de utilizador
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void desactivar(@PathVariable UUID id) {
        fornecedorService.desactivar(id);
    }
}
