package com.api.kwanza_services.Util;

import java.util.Locale;

/**
 * registo grava sempre o e-mail em minúsculas e o telefone em E.164(formato de Angola)
 */
public final class ContactoNormalizer {

    private static final String TELEFONE_ANGOLANO = "^\\+2449\\d{8}$";

    private ContactoNormalizer() {
    }

    /**
     * Normalização tolerante, usada no login: nunca lança excepção.
     * Um valor inválido simplesmente não corresponde a nenhuma conta.
     */
    public static String email(String value) {
        if (value == null) {
            return "";
        }
        return value.trim().toLowerCase(Locale.ROOT);
    }

    /**
     * Normalização tolerante do telefone
     */
    public static String telefone(String value) {
        if (value == null || value.isBlank()) {
            return "";
        }

        String digits = value.replaceAll("[^0-9+]", "");

        if (digits.startsWith("00")) {
            digits = "+" + digits.substring(2);
        }
        if (digits.matches("^9\\d{8}$")) {
            digits = "+244" + digits;
        }
        if (digits.matches("^2449\\d{8}$")) {
            digits = "+" + digits;
        }

        return digits;
    }

    /**
     * Normalização estrita, usada no registo: garante um telefone angolano válido.
     */
    public static String telefoneValido(String value) {
        String normalizado = telefone(value);

        if (!normalizado.matches(TELEFONE_ANGOLANO)) {
            throw new IllegalArgumentException(
                "Número de telefone inválido. Use o formato +2449XXXXXXXX"
            );
        }

        return normalizado;
    }
}
