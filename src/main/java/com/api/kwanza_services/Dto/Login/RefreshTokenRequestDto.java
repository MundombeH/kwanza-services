package com.api.kwanza_services.Dto.Login;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RefreshTokenRequestDto {
    @NotBlank(message = "Refresh token é obrigatório")
    private  String refreshToken;
}
