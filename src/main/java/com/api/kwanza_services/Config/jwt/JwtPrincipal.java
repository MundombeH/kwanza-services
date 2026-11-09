package com.api.kwanza_services.Config.jwt;

import java.util.Set;
import java.util.UUID;


public record JwtPrincipal(
    UUID userId,
    String perfil,
    Set<String> roles
) {
}
