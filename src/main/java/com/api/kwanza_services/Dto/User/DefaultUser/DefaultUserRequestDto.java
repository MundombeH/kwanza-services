package com.api.kwanza_services.Dto.User.DefaultUser;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class DefaultUserRequestDto {


    @NotBlank(message = "O Nome completo é obrigatório")
    private String nome;
    @Email(message = "Formato de e-mail errado")
    @NotBlank(message = "O E-mail é obrigatório")
    private String email;

    @NotBlank(message = "A password é obrigatória")
    @Size(min = 6, message = "A password tem de ter pelo menos 6 caracteres")
    private String password;

    @NotBlank(message = "O número de telefone é obrigatória ")
    private String numeroDeTelefone;



}
