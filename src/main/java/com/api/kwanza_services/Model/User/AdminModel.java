package com.api.kwanza_services.Model.User;

import com.api.kwanza_services.Model.DefaultModel;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name= "USUARIO_ADMIN")
@Setter
@Getter
public class AdminModel extends DefaultModel {

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private DefaultUserModel usuario;

}
