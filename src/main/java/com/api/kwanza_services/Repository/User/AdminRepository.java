package com.api.kwanza_services.Repository.User;

import com.api.kwanza_services.Model.User.AdminModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<AdminModel, UUID> {

    Optional<AdminModel> findByUsuarioId(UUID usuarioId);
}
