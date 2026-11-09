package com.api.kwanza_services.Model.Roles_Permisisons;

import com.api.kwanza_services.Model.DefaultModel;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
@Entity
@Table(name = "PERMISSIONS")
@Setter
@Getter
public class PermissionModel extends DefaultModel {

    @Column(nullable = false, unique = true)
    private String descricao;


    @ManyToMany(mappedBy = "permissions")
    private Set<RoleModel> roles = new HashSet<>();
}
