package com.api.kwanza_services.Enums;

public enum TipoPerfilEnum {
    CLIENTE("Cliente"),
    FORNECEDOR("Fornecedor de serviços"),
    GESTOR("Gestor de vendas"),
    ADMIN("Administrador do sistema");

    private final String descricao;

    TipoPerfilEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    /**
     * Nome da role usado pelo Spring Security.
     * Ex.: CLIENTE -> ROLE_CLIENTE
     */
    public String getRole() {
        return "ROLE_" + name();
    }
}
