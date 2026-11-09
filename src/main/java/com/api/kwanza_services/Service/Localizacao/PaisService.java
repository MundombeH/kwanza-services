package com.api.kwanza_services.Service.Localizacao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.api.kwanza_services.Dto.Localizacao.Pais.PaisesRequestDTO;
import com.api.kwanza_services.Dto.Localizacao.Pais.PaisesResponseDTO;
import com.api.kwanza_services.Repository.Localizacao.PaisesRepository;
import com.api.kwanza_services.Model.Localizacao.PaisModel;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class PaisService {

    private final PaisesRepository paisesRepository;

    PaisService(PaisesRepository paisesRepository) {
        this.paisesRepository = paisesRepository;
    }

    public List<PaisesResponseDTO> getAll() {

        return paisesRepository.findByIsActiveTrue().stream().map(this::mapToDto).collect(Collectors.toList());
    }



    public PaisesResponseDTO getOne(UUID id) {
        PaisModel paisModel = paisesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("País não encontrado"));


        return mapToDto(paisModel);
    }

    @Transactional
    public PaisesResponseDTO create(PaisesRequestDTO paisesRequestDTO) throws Exception {


        paisesRepository.existsByDescricao(paisesRequestDTO.getDescricao()).orElseThrow(() -> new EntityNotFoundException("Erro este país já existe no sistema!"));


        PaisModel paisModel = new PaisModel();
        paisModel.setDescricao(paisesRequestDTO.getDescricao());

        PaisModel salvo = paisesRepository.save(paisModel);

        return mapToDto(salvo);
    }

    public PaisesResponseDTO put(UUID id, PaisesRequestDTO paisesRequestDTO) throws Exception {
        PaisModel paisModel = paisesRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new RuntimeException("País não encontrado"));

        if (paisesRequestDTO.getDescricao().startsWith(" ")) {
            throw new Exception("O Nome do País não deve começar com espaço em branco");
        }

        paisModel.setDescricao(paisesRequestDTO.getDescricao());

        PaisModel salvo = paisesRepository.save(paisModel);


        return mapToDto(salvo);
    }

    public void delete(UUID id) throws Exception {
        PaisModel paisesModal = paisesRepository.findByIdAndIsActiveTrue(id).orElseThrow(() -> new EntityNotFoundException("País não encontrado ou desativado!"));
        paisesModal.setIsActive(false);
        paisesModal.setUpdatedAt(LocalDateTime.now());
        paisesRepository.save(paisesModal);
    }



    private PaisesResponseDTO mapToDto(PaisModel pais) {
        var dto = new PaisesResponseDTO();
        dto.setId(pais.getId());
        dto.setDescricao(pais.getDescricao());
        dto.setIsActive(pais.getIsActive());
        dto.setCreatedAt(pais.getCreatedAt());
        dto.setUpdatedAt(pais.getUpdatedAt());
        return dto;
    }
}
