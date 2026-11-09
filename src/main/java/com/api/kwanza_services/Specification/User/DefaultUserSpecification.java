package com.api.kwanza_services.Specification.User;

import com.api.kwanza_services.Enums.TipoPerfilEnum;
import com.api.kwanza_services.Model.User.ClienteModel;
import com.api.kwanza_services.Model.User.DefaultUserModel;
import com.api.kwanza_services.Model.User.FornecedorModel;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class DefaultUserSpecification {

    private DefaultUserSpecification() {
    }

    public static Specification<DefaultUserModel> ativo() {
        return (root, query, cb) -> cb.isTrue(root.get("isActive"));
    }

    /**
     * O bairro não está no utilizador base: está no perfil de cliente e de fornecedor.
     * Por isso o filtro é feito com subqueries sobre essas duas tabelas.
     */
    public static Specification<DefaultUserModel> porBairro(UUID bairroId) {
        return (root, query, cb) -> {
            if (bairroId == null) {
                return null;
            }

            var clientes = query.subquery(UUID.class);
            var cliente = clientes.from(ClienteModel.class);
            clientes
                .select(cliente.get("usuario").get("id"))
                .where(cb.equal(cliente.get("bairro").get("id"), bairroId));

            var fornecedores = query.subquery(UUID.class);
            var fornecedor = fornecedores.from(FornecedorModel.class);
            fornecedores
                .select(fornecedor.get("usuario").get("id"))
                .where(cb.equal(fornecedor.get("bairro").get("id"), bairroId));

            return cb.or(
                root.get("id").in(clientes),
                root.get("id").in(fornecedores)
            );
        };
    }

    public static Specification<DefaultUserModel> porTipoPerfil(TipoPerfilEnum perfil) {
        return (root, query, cb) -> perfil == null
                ? null
                : cb.equal(root.get("perfilAtivo"), perfil);
    }
}
