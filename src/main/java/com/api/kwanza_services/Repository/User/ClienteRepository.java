package com.api.kwanza_services.Repository.User;

import com.api.kwanza_services.Model.User.ClienteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteModel, UUID> {

    Optional<ClienteModel> findByUsuarioId(UUID usuarioId);
}
