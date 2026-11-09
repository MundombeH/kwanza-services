package com.api.kwanza_services.Dto.User.DefaultUser;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AlterarPasswordDto {


    @NotBlank(message = "A password atual é obrigatória")
    @Size(min = 6, message = "A password deve ter no mínimo 6 caracteres")
    private String currentPassword;
    @NotBlank(message = "A nova password é obrigatória")
    @Size(min = 6, message = "A password deve ter no mínimo 6 caracteres")

    private String newPassword;
    @NotBlank(message = "A confirmação da password é obrigatória")
    @Size(min = 6, message = "A password deve ter no mínimo 6 caracteres")
    private String confirmPassword;
}
