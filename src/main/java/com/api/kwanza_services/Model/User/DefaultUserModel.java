package com.api.kwanza_services.Model.User;

import com.api.kwanza_services.Enums.TipoPerfilEnum;
import com.api.kwanza_services.Model.DefaultModel;
import com.api.kwanza_services.Model.Roles_Permisisons.RoleModel;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "USUARIO")
@Getter
@Setter
public class DefaultUserModel extends DefaultModel implements UserDetails {

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "numero_de_telefone", nullable = false, unique = true)
    private String numeroDeTelefone;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;


    @Enumerated(EnumType.STRING)
    @Column(name = "perfil_ativo")
    private TipoPerfilEnum perfilAtivo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USERS_ROLES",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleModel> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        roles.stream()
                .filter(role -> Boolean.TRUE.equals(role.getIsActive()))
                .forEach(role -> {
                    // Roles com prefixo ROLE_ para funcionar com hasRole(...)
                    authorities.add(new SimpleGrantedAuthority(normalizarRole(role.getDescricao())));

                    // Permissões ficam sem prefixo
                    Optional.ofNullable(role.getPermissions())
                            .orElseGet(Set::of)
                            .stream()
                            .filter(perm -> Boolean.TRUE.equals(perm.getIsActive()))
                            .map(perm -> new SimpleGrantedAuthority(perm.getDescricao()))
                            .forEach(authorities::add);
                });

        return authorities;
    }

    private String normalizarRole(String descricao) {
        return descricao.startsWith("ROLE_") ? descricao : "ROLE_" + descricao;
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return Boolean.TRUE.equals(isActive);
    }
}
