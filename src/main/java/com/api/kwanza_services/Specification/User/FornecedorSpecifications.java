package com.api.kwanza_services.Specification.User;

import com.api.kwanza_services.Enums.SexoEnum;
import com.api.kwanza_services.Model.User.FornecedorModel;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public final class FornecedorSpecifications {

    private FornecedorSpecifications() {
    }

    public static Specification<FornecedorModel> ativo() {
        return (root, query, cb) -> cb.isTrue(root.get("isActive"));
    }

    public static Specification<FornecedorModel> comBairro(UUID bairroId) {
        return (root, query, cb) -> bairroId == null
                ? null
                : cb.equal(root.get("bairro").get("id"), bairroId);
    }

    public static Specification<FornecedorModel> comSexo(SexoEnum sexo) {
        return (root, query, cb) -> sexo == null
                ? null
                : cb.equal(root.get("sexo"), sexo);
    }

    public static Specification<FornecedorModel> identidadeValidada(Boolean validada) {
        return (root, query, cb) -> validada == null
                ? null
                : cb.equal(root.get("identidadeValidada"), validada);
    }

    public static Specification<FornecedorModel> nomeOuEmail(String termo) {
        return (root, query, cb) -> {
            if (termo == null || termo.isBlank()) {
                return null;
            }

            String like = "%" + termo.trim().toLowerCase() + "%";
            var usuario = root.join("usuario");

            return cb.or(
                    cb.like(cb.lower(usuario.get("nome")), like),
                    cb.like(cb.lower(usuario.get("email")), like)
            );
        };
    }
}
