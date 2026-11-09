package com.api.kwanza_services.Config;

import com.api.kwanza_services.Enums.TipoPerfilEnum;
import com.api.kwanza_services.Model.Roles_Permisisons.RoleModel;
import com.api.kwanza_services.Model.User.DefaultUserModel;
import com.api.kwanza_services.Repository.User.DefaultUserRepository;
import com.api.kwanza_services.Service.Roles_Permisisons.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Cria um utilizador administrador para desenvolvimento/teste de login.
 * Nunca corre com o perfil "prod" nem sem app.dev-user.enabled=true.
 */
@Slf4j
@Component
@Profile("!prod")
@ConditionalOnProperty(name = "app.dev-user.enabled", havingValue = "true")
@RequiredArgsConstructor
public class DevUserSeeder implements ApplicationRunner {

    private final DefaultUserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.dev-user.email:admin@kwanza.local}")
    private String email;

    @Value("${app.dev-user.password:admin123}")
    private String password;

    @Value("${app.dev-user.phone:+244900000000}")
    private String phone;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (userRepository.findByEmail(email).isPresent()) {
            log.info("Utilizador de desenvolvimento já existe: {}", email);
            return;
        }

        RoleModel role = roleService.obterOuCriar(TipoPerfilEnum.ADMIN);

        DefaultUserModel user = new DefaultUserModel();
        user.setNome("Administrador de Desenvolvimento");
        user.setEmail(email);
        user.setNumeroDeTelefone(phone);
        user.setPassword(passwordEncoder.encode(password));
        user.setPerfilAtivo(TipoPerfilEnum.ADMIN);
        user.setIsActive(true);
        user.getRoles().add(role);
        userRepository.save(user);

        log.warn(
            "Utilizador de desenvolvimento criado: {} (telefone: {}). " +
            "A palavra-passe vem de DEV_USER_PASSWORD.",
            email,
            phone
        );
    }
}
