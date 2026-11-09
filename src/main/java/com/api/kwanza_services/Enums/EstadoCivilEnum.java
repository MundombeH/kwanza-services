package com.api.kwanza_services.Enums;

public enum EstadoCivilEnum {
    SOLTEIRO("SOLTEIRO"),
    CASADO("CASADO"),
    DIVORCIADO("DIVORCIADO"),
    VIUVO("VIUVO"),
    SOLTEIRA("SOLTEIRA"),
    CASADA("CASADA"),
    DIVORCIADA("DIVORCIADA"),
    VIUVA("VIUVA");

    private final String estadoCivil;

    EstadoCivilEnum(String estadoCivil) {
        this.estadoCivil = estadoCivil;

    }
    public String getEstadoCivil(){
        return estadoCivil;
    }
}
