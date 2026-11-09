package com.api.kwanza_services.Enums;

public enum TipoDocumentoEnum {
    BI("Bilhete de identidade"),
    PASSAPORTE("Passaporte");


    private final String tipoDocumento;

    TipoDocumentoEnum(String sexo){
        this.tipoDocumento = sexo;
    }
    private String getTipoDocumento(){
        return this.tipoDocumento;
    }
}
