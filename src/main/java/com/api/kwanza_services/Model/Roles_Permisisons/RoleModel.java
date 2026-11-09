package com.api.kwanza_services.Model.Roles_Permisisons;

import com.api.kwanza_services.Model.DefaultModel;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;

@Entity
@Table(name="ROLES")
@Setter
@Getter
public class RoleModel extends DefaultModel {

    @Column(name = "descricao",nullable = false, unique = true)
    private String descricao;


    @ManyToMany
    @JoinTable(
            name = "roles_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<PermissionModel> permissions = new HashSet<>();
}
