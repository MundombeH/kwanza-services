package com.api.kwanza_services.Service.User;

import com.api.kwanza_services.Dto.User.Fornecedor.FornecedorFiltroDto;
import com.api.kwanza_services.Dto.User.Fornecedor.FornecedorResponseDto;
import com.api.kwanza_services.Exception.RecursoNaoEncontradoException;
import com.api.kwanza_services.Mapper.User.FornecedorMapper;
import com.api.kwanza_services.Model.User.FornecedorModel;
import com.api.kwanza_services.Repository.User.FornecedorRepository;
import com.api.kwanza_services.Specification.User.FornecedorSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;
    private final FornecedorMapper fornecedorMapper;

    @Transactional(readOnly = true)
    public Page<FornecedorResponseDto> pesquisar(FornecedorFiltroDto filtro, Pageable pageable) {
        Specification<FornecedorModel> spec = Specification.allOf(
                FornecedorSpecifications.ativo(),
                FornecedorSpecifications.comBairro(filtro.getBairroId()),
                FornecedorSpecifications.comSexo(filtro.getSexo()),
                FornecedorSpecifications.identidadeValidada(filtro.getIdentidadeValidada()),
                FornecedorSpecifications.nomeOuEmail(filtro.getQ())
        );

        return fornecedorRepository.findAll(spec, pageable)
                .map(fornecedorMapper::toResponse);
    }


    @Transactional
    public void desactivar(UUID id) {
        FornecedorModel fornecedor = fornecedorRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Fornecedor não encontrado: " + id
                ));

        fornecedor.setIsActive(false);
        fornecedorRepository.save(fornecedor);
    }
}
