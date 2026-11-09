package com.api.kwanza_services.Repository.Localizacao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.api.kwanza_services.Model.Localizacao.BairroModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BairrosRepository extends JpaRepository<BairroModel, UUID>{

    Optional<BairroModel> findByDescricaoAndIsActiveTrue(String descricao);

    List<BairroModel> findByMunicipioId( UUID municipioId);

    Optional<BairroModel> findByIdAndIsActiveTrue(UUID id);
}
