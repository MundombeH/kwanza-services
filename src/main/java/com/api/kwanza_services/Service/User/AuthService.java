package com.api.kwanza_services.Service.User;

import com.api.kwanza_services.Config.jwt.JwtService;
import com.api.kwanza_services.Dto.Login.LoginRequestDto;
import com.api.kwanza_services.Dto.Login.TokenResponseDto;
import com.api.kwanza_services.Model.User.DefaultUserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public TokenResponseDto login(LoginRequestDto request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getLogin().trim(),
                request.getPassword()
            )
        );

        DefaultUserModel user = (DefaultUserModel) authentication.getPrincipal();

        Set<String> authorities = authentication.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toCollection(LinkedHashSet::new));

        Set<String> roles = authorities
            .stream()
            .filter(authority -> authority.startsWith("ROLE_"))
            .collect(Collectors.toCollection(LinkedHashSet::new));

        Set<String> permissions = authorities
            .stream()
            .filter(authority -> !authority.startsWith("ROLE_"))
            .collect(Collectors.toCollection(LinkedHashSet::new));

        assert user != null;
        String perfil = user.getPerfilAtivo() != null
            ? user.getPerfilAtivo().name()
            : null;

        String accessToken = jwtService.generateToken(
            user.getId(),
            roles,
            permissions,
            perfil
        );

        return new TokenResponseDto(
            accessToken,
            null,
            "Bearer",
            jwtService.getExpirationMs() / 1000,
            user.getId(),
            user.getNome(),
            user.getEmail(),
            user.getPerfilAtivo(),
            roles
        );
    }
}
