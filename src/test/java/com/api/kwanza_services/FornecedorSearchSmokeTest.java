package com.api.kwanza_services;

import com.api.kwanza_services.Dto.User.Fornecedor.FornecedorFiltroDto;
import com.api.kwanza_services.Enums.SexoEnum;
import com.api.kwanza_services.Service.User.FornecedorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class FornecedorSearchSmokeTest {

    @Autowired
    private FornecedorService fornecedorService;

    @Test
    void pesquisaComTodosOsFiltros() {
        FornecedorFiltroDto filtro = new FornecedorFiltroDto();
        filtro.setQ("teste");
        filtro.setBairroId(UUID.randomUUID());
        filtro.setSexo(SexoEnum.MASCULINO);
        filtro.setIdentidadeValidada(true);

        var page = fornecedorService.pesquisar(filtro, PageRequest.of(0, 10));

        assertNotNull(page);
    }
}
