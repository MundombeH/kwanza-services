package com.api.kwanza_services.Service.Roles_Permisisons;

import com.api.kwanza_services.Enums.TipoPerfilEnum;
import com.api.kwanza_services.Exception.RecursoNaoEncontradoException;
import com.api.kwanza_services.Model.Roles_Permisisons.RoleModel;
import com.api.kwanza_services.Model.User.DefaultUserModel;
import com.api.kwanza_services.Repository.Roles_Permisisons.RoleRepository;
import com.api.kwanza_services.Repository.User.DefaultUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final DefaultUserRepository userRepository;

    @Transactional
    public RoleModel obterOuCriar(TipoPerfilEnum tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("O tipo de perfil é obrigatório");
        }

        String descricao = tipo.getRole();

        return roleRepository.findByDescricao(descricao)
                .map(role -> {
                    if (!Boolean.TRUE.equals(role.getIsActive())) {
                        role.setIsActive(true);
                        return roleRepository.save(role);
                    }
                    return role;
                })
                .orElseGet(() -> {
                    RoleModel nova = new RoleModel();
                    nova.setDescricao(descricao);
                    nova.setIsActive(true);
                    return roleRepository.save(nova);
                });
    }

    /**
     * Atribui a role do perfil ao utilizador.
     */
    @Transactional
    public boolean atribuirRole(DefaultUserModel usuario, TipoPerfilEnum tipo) {
        if (usuario == null) {
            throw new IllegalArgumentException("O utilizador é obrigatório");
        }

        if (temRole(usuario, tipo)) {
            return false;
        }

        usuario.getRoles().add(obterOuCriar(tipo));
        userRepository.save(usuario);
        return true;
    }

    /**
     * Remove a role do perfil ao utilizador, se a tiver.

     */
    @Transactional
    public boolean removerRole(DefaultUserModel usuario, TipoPerfilEnum tipo) {
        if (usuario == null || tipo == null) {
            return false;
        }

        String descricao = tipo.getRole();
        boolean removeu = usuario.getRoles()
                .removeIf(role -> descricao.equals(role.getDescricao()));

        if (removeu) {
            userRepository.save(usuario);
        }

        return removeu;
    }

    /**
     * Verifica se o utilizador tem a role activa do perfil.
     */
    public boolean temRole(DefaultUserModel usuario, TipoPerfilEnum tipo) {
        if (usuario == null || tipo == null || usuario.getRoles() == null) {
            return false;
        }

        String descricao = tipo.getRole();

        return usuario.getRoles().stream()
                .anyMatch(role ->
                        Boolean.TRUE.equals(role.getIsActive())
                        && descricao.equals(role.getDescricao())
                );
    }

    @Transactional
    public boolean atribuirRole(UUID usuarioId, TipoPerfilEnum tipo) {
        return atribuirRole(obterUtilizadorActivo(usuarioId), tipo);
    }

    @Transactional
    public boolean removerRole(UUID usuarioId, TipoPerfilEnum tipo) {
        return removerRole(obterUtilizadorActivo(usuarioId), tipo);
    }

    @Transactional(readOnly = true)
    public boolean temRole(UUID usuarioId, TipoPerfilEnum tipo) {
        return temRole(obterUtilizadorActivo(usuarioId), tipo);
    }

    private DefaultUserModel obterUtilizadorActivo(UUID usuarioId) {
        if (usuarioId == null) {
            throw new IllegalArgumentException("O utilizador é obrigatório");
        }

        return userRepository.findByIdAndIsActiveTrue(usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Utilizador não encontrado: " + usuarioId
                ));
    }
}
