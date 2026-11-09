package com.api.kwanza_services.Config.jwt;

import java.util.Set;
import java.util.UUID;

public record JwtAuthPayload(
    UUID userId,
    Set<String> roles,
    Set<String> permissions,
    String perfil
) {
}
