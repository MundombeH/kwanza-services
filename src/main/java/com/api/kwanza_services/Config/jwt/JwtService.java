package com.api.kwanza_services.Config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.time.Instant;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private final Algorithm algorithm;
    private final JWTVerifier verifier;
    private final String issuer;
    private final long expirationMs;
    private final long refreshExpirationMs;

    public JwtService(
        @Value("${jwt.secret}") String secret,
        @Value("${jwt.issuer}") String issuer,
        @Value("${jwt.expiration-ms}") long expirationMs,
        @Value(
            "${jwt.refresh-expiration-ms}"
        ) long refreshExpirationMs
    ) {
        this.algorithm = Algorithm.HMAC256(secret);
        this.issuer = issuer;
        this.expirationMs = expirationMs;
        this.refreshExpirationMs = refreshExpirationMs;
        this.verifier = JWT.require(this.algorithm)
            .withIssuer(this.issuer)
            .build();
    }

    public String generateToken(
        UUID userId,
        Set<String> roles,
        Set<String> permissions,
        String perfil
    ) {
        Instant now = Instant.now();

        Set<String> normalizedRoles = normalizeRoles(roles);
        Set<String> safePermissions = safeSet(permissions);

        var builder = JWT.create()
            .withIssuer(issuer)
            .withSubject(userId.toString())
            .withClaim("userId", userId.toString())
            .withClaim("roles", List.copyOf(normalizedRoles))
            .withClaim("permissions", List.copyOf(safePermissions))
            .withIssuedAt(now)
            .withExpiresAt(now.plusMillis(expirationMs));

        if (perfil != null && !perfil.isBlank()) {
            builder.withClaim("perfil", perfil);
        }

        return builder.sign(algorithm);
    }

    public JwtAuthPayload validateToken(String token) {
        try {
            DecodedJWT decodedJWT = verifier.verify(token);

            String userIdClaim = decodedJWT.getClaim("userId").asString();
            if (userIdClaim == null || userIdClaim.isBlank()) {
                userIdClaim = decodedJWT.getSubject();
            }

            UUID userId = UUID.fromString(userIdClaim);
            Set<String> roles = normalizeRoles(
                toSet(decodedJWT.getClaim("roles").asList(String.class))
            );
            Set<String> permissions = safeSet(
                toSet(decodedJWT.getClaim("permissions").asList(String.class))
            );
            String perfil = decodedJWT.getClaim("perfil").asString();

            return new JwtAuthPayload(userId, roles, permissions, perfil);
        } catch (JWTVerificationException | IllegalArgumentException ex) {
            return null;
        }
    }

    public long getExpirationMs() {
        return expirationMs;
    }

    public long getRefreshExpirationMs() {
        return refreshExpirationMs;
    }

    private Set<String> normalizeRoles(Set<String> roles) {
        return safeSet(roles)
            .stream()
            .map(this::normalizeRole)
            .filter(Objects::nonNull)
            .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private String normalizeRole(String role) {
        if (role == null || role.isBlank()) {
            return null;
        }

        String trimmed = role.trim();
        return trimmed.startsWith("ROLE_") ? trimmed : "ROLE_" + trimmed;
    }

    private Set<String> safeSet(Set<String> values) {
        if (values == null) {
            return Collections.emptySet();
        }

        return values
            .stream()
            .filter(Objects::nonNull)
            .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private Set<String> toSet(List<String> values) {
        if (values == null) {
            return Collections.emptySet();
        }
        return new LinkedHashSet<>(values);
    }
}
