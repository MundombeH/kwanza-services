package com.api.kwanza_services.Dto.Login;

import com.api.kwanza_services.Enums.TipoPerfilEnum;

import java.util.Set;
import java.util.UUID;


public record TokenResponseDto(
    String accessToken,
    String refreshToken,
    String tokenType,
    long expiresIn,
    UUID userId,
    String nome,
    String email,
    TipoPerfilEnum perfilAtivo,
    Set<String> roles
) {
}
