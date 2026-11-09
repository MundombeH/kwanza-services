package com.api.kwanza_services.Repository.Roles_Permisisons;

import com.api.kwanza_services.Model.Roles_Permisisons.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, UUID> {

    Optional<RoleModel> findByDescricao(String descricao);
}
