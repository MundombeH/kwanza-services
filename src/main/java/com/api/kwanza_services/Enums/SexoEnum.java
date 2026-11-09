package com.api.kwanza_services.Enums;

public enum SexoEnum {
    MASCULINO("Masculino"),
    FEMININO("Feminino");


    private final String sexo;

    SexoEnum(String sexo){
        this.sexo = sexo;
    }
    private String getSexo(){
        return this.sexo;
    }
}
