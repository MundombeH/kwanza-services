package com.api.kwanza_services.Service.User;

import com.api.kwanza_services.Repository.User.DefaultUserRepository;
import com.api.kwanza_services.Util.ContactoNormalizer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final DefaultUserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        if (login.isBlank()) {
            throw new UsernameNotFoundException("Credenciais inválidas");
        }

        String identifier = login.trim();
        boolean isEmail = identifier.contains("@");

        String email = isEmail ? ContactoNormalizer.email(identifier) : identifier;
        String telefone = isEmail ? identifier : ContactoNormalizer.telefone(identifier);

        return userRepository.findActiveByTelefoneOrEmail(telefone, email)
            .orElseThrow(() -> new UsernameNotFoundException("Credenciais inválidas"));
    }
}
