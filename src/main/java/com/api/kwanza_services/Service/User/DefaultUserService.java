package com.api.kwanza_services.Service.User;

import com.api.kwanza_services.Dto.User.DefaultUser.AlterarPasswordDto;
import com.api.kwanza_services.Dto.User.DefaultUser.AtualizarUtilizadorRequestDto;
import com.api.kwanza_services.Dto.User.DefaultUser.DefaultUserFiltro;
import com.api.kwanza_services.Dto.User.DefaultUser.DefaultUserRequestDto;
import com.api.kwanza_services.Dto.User.DefaultUser.DefaultUserResponseDto;
import com.api.kwanza_services.Exception.ConflitoException;
import com.api.kwanza_services.Exception.RecursoNaoEncontradoException;
import com.api.kwanza_services.Mapper.User.DefaultUserMapper;
import com.api.kwanza_services.Model.User.DefaultUserModel;
import com.api.kwanza_services.Repository.User.DefaultUserRepository;
import com.api.kwanza_services.Specification.User.DefaultUserSpecification;
import com.api.kwanza_services.Util.ContactoNormalizer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DefaultUserService {

    private final DefaultUserRepository userRepository;
    private final DefaultUserMapper defaultUserMapper;
    private final PasswordEncoder passwordEncoder;

    public DefaultUserService(DefaultUserRepository userRepository, DefaultUserMapper defaultUserMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.defaultUserMapper = defaultUserMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public Page<DefaultUserResponseDto> getAllUsers(DefaultUserFiltro filtro, Pageable pageable){
        Specification<DefaultUserModel> spec = Specification.allOf(
                DefaultUserSpecification.ativo(),
                DefaultUserSpecification.porBairro(filtro.getBairroId()),
                DefaultUserSpecification.porTipoPerfil(filtro.getTipoPerfil()));

        return userRepository.findAll(spec, pageable)
                .map(defaultUserMapper::response);
    }

    @Transactional(readOnly = true)
    public DefaultUserResponseDto buscarPorId(UUID id) {
        return defaultUserMapper.response(buscarModeloPorId(id));
    }

    /**
     * Devolve a entidade activa. Usado pela orquestração de perfis e roles.
     */
    @Transactional(readOnly = true)
    public DefaultUserModel buscarModeloPorId(UUID id) {
        return userRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Utilizador não encontrado: " + id
                ));
    }

    /**
     * Cria a conta base do utilizador.

     */
    @Transactional
    public DefaultUserModel criarConta(DefaultUserRequestDto dto) {
        String email = ContactoNormalizer.email(dto.getEmail());
        String telefone = ContactoNormalizer.telefoneValido(dto.getNumeroDeTelefone());

        if (userRepository.existsByEmailAndIsActiveTrue(email)) {
            throw new ConflitoException("Já existe uma conta com o e-mail " + email);
        }
        if (userRepository.existsByNumeroDeTelefoneAndIsActiveTrue(telefone)) {
            throw new ConflitoException("Já existe uma conta com o número de telefone " + telefone);
        }

        DefaultUserModel user = new DefaultUserModel();
        user.setNome(dto.getNome().trim());
        user.setEmail(email);
        user.setNumeroDeTelefone(telefone);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setIsActive(true);

        return userRepository.save(user);
    }

    @Transactional
    public DefaultUserResponseDto updatePerfil(UUID id, AtualizarUtilizadorRequestDto dto){
        DefaultUserModel user = buscarModeloPorId(id);

        String email = ContactoNormalizer.email(dto.getEmail());
        String telefone = ContactoNormalizer.telefoneValido(dto.getNumeroDeTelefone());

        if (!user.getEmail().equals(email) && userRepository.existsByEmailAndIsActiveTrue(email)) {
            throw new ConflitoException("Este email ja existe no sistema: " + email);
        }
        if (!user.getNumeroDeTelefone().equals(telefone)
                && userRepository.existsByNumeroDeTelefoneAndIsActiveTrue(telefone)) {
            throw new ConflitoException("Este numero de telefone ja existe no sistema: " + telefone);
        }

        user.setEmail(email);
        user.setNome(dto.getNome().trim());
        user.setNumeroDeTelefone(telefone);

        DefaultUserModel updated = userRepository.save(user);

        return defaultUserMapper.response(updated);
    }

    @Transactional
    public String AlterPassword(AlterarPasswordDto dto, UUID userId){
        DefaultUserModel user = buscarModeloPorId(userId);

        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Palavra-passe atual incorreta");
        }

        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("As palavras-passe não coincidem");
        }
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
        return "Palavra-passe atualizada com sucesso";
    }

    @Transactional
    public void DeleteUser(UUID id){
        DefaultUserModel user = buscarModeloPorId(id);

        user.setIsActive(false);
        userRepository.save(user);
    }
}
