package com.api.kwanza_services.Exception;

/**
 * Recurso referenciado pelo pedido não existe. Mapeado para HTTP 404.
 */
public class RecursoNaoEncontradoException extends RuntimeException {

    public RecursoNaoEncontradoException(String message) {
        super(message);
    }
}
