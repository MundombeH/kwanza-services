package com.api.kwanza_services.Repository.User;

import com.api.kwanza_services.Model.User.GestorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GestorRepository extends JpaRepository<GestorModel, UUID> {

    Optional<GestorModel> findByNumeroDoFuncionarioAndIsActiveTrue(String numeroDoFuncionario);

    List<GestorModel> findByIdentidadeValidadaTrue();

    Optional<GestorModel> findByUsuarioId(UUID usuarioId);
}
