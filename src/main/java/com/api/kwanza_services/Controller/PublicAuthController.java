package com.api.kwanza_services.Controller;

import com.api.kwanza_services.Dto.Login.LoginRequestDto;
import com.api.kwanza_services.Dto.Login.TokenResponseDto;
import com.api.kwanza_services.Dto.User.Cliente.RegistoClienteRequestDto;
import com.api.kwanza_services.Dto.User.DefaultUser.DefaultUserResponseDto;
import com.api.kwanza_services.Service.User.AuthService;
import com.api.kwanza_services.Service.User.RegistoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/public/auth")
@RequiredArgsConstructor
public class PublicAuthController {

    private final AuthService authService;
    private final RegistoService registoService;

    @PostMapping("/login")
    public TokenResponseDto login(@Valid @RequestBody LoginRequestDto request) {
        return authService.login(request);
    }

    @PostMapping("/register/cliente")
    @ResponseStatus(HttpStatus.CREATED)
    public DefaultUserResponseDto register(@Valid @RequestBody RegistoClienteRequestDto request) {
        return registoService.registarCliente(request);
    }

}
