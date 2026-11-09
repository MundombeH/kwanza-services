package com.api.kwanza_services.Exception;

/**
 * Conflito com o estado actual do sistema para dados ja usados
 * Mapeado para HTTP 409.
 */
public class ConflitoException extends RuntimeException {

    public ConflitoException(String message) {
        super(message);
    }
}
