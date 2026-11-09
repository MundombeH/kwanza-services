package com.api.kwanza_services.Repository.User;

import com.api.kwanza_services.Model.User.DefaultUserModel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DefaultUserRepository extends JpaRepository<DefaultUserModel, UUID>, JpaSpecificationExecutor<DefaultUserModel> {



    @Override
    @EntityGraph(attributePaths = {"roles", "roles.permissions"})
    Optional<DefaultUserModel> findById(UUID id);

    @EntityGraph(attributePaths = {"roles", "roles.permissions"})
    Optional<DefaultUserModel> findByIdAndIsActiveTrue(UUID id);

    @EntityGraph(attributePaths = {"roles", "roles.permissions"})
    Optional<DefaultUserModel> findByEmail(String email);

    @EntityGraph(attributePaths = {"roles", "roles.permissions"})
    Optional<DefaultUserModel> findByNumeroDeTelefone(String telefone);

    boolean existsByEmailAndIsActiveTrue(String email);
    boolean existsByNumeroDeTelefoneAndIsActiveTrue(String telefone);

    /**
     * Procura um utilizador activo pelo telefone OU pelo email.
     */
    @Query("""
            select u from DefaultUserModel u
            where u.isActive = true
              and (u.numeroDeTelefone = :telefone or lower(u.email) = lower(:email))
            """)
    @EntityGraph(attributePaths = {"roles", "roles.permissions"})
    Optional<DefaultUserModel> findActiveByTelefoneOrEmail(@Param("telefone") String telefone,
                                                           @Param("email") String email);
}
