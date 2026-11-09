package com.api.kwanza_services.Repository.User;

import com.api.kwanza_services.Model.User.FornecedorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FornecedorRepository
        extends JpaRepository<FornecedorModel, UUID>, JpaSpecificationExecutor<FornecedorModel> {

    Optional<FornecedorModel> findByUsuarioId(UUID usuarioId);

    List<FornecedorModel> findByBairroIdAndIsActiveTrue(UUID bairroId);

    Optional<FornecedorModel> findByIdAndIsActiveTrue(UUID id);
}
