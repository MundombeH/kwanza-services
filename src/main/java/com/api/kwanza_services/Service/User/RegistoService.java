package com.api.kwanza_services.Service.User;

import com.api.kwanza_services.Dto.User.Admin.AdminRequestDto;
import com.api.kwanza_services.Dto.User.Cliente.ClienteRequestDto;
import com.api.kwanza_services.Dto.User.Cliente.RegistoClienteRequestDto;
import com.api.kwanza_services.Dto.User.DefaultUser.DefaultUserResponseDto;
import com.api.kwanza_services.Dto.User.Fornecedor.RegistoFornecedorRequestDto;
import com.api.kwanza_services.Dto.User.Gestor.GestorRequestDto;
import com.api.kwanza_services.Enums.TipoPerfilEnum;
import com.api.kwanza_services.Exception.RecursoNaoEncontradoException;
import com.api.kwanza_services.Mapper.User.DefaultUserMapper;
import com.api.kwanza_services.Model.Localizacao.BairroModel;
import com.api.kwanza_services.Model.User.DefaultUserModel;
import com.api.kwanza_services.Model.User.DocumentoDeIdentificacaoModel;
import com.api.kwanza_services.Repository.Localizacao.BairrosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class RegistoService {

    private final DefaultUserService defaultUserService;
    private final PerfilService perfilService;
    private final DefaultUserMapper defaultUserMapper;
    private final BairrosRepository bairrosRepository;
    private final DocumentoDeIdentificacaoService documentoDeIdentificacaoService;

    @Transactional
    public DefaultUserResponseDto registarCliente(RegistoClienteRequestDto dto) {
        BairroModel bairro = bairrosRepository.findByIdAndIsActiveTrue(dto.getBairroId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Bairro não encontrado: " + dto.getBairroId()
                ));

        DefaultUserModel usuario = defaultUserService.criarConta(dto);
        perfilService.criarPerfilCliente(usuario, dto.getSexo(), bairro);

        return defaultUserMapper.response(usuario);
    }


    /**
     * Adiciona o perfil de cliente a um utilizador já existente.
     */
    @Transactional
    public DefaultUserResponseDto adicionarPerfilCliente(ClienteRequestDto dto) {
        DefaultUserModel usuario = defaultUserService.buscarModeloPorId(dto.getUsuario());
        BairroModel bairro = bairrosRepository.findByIdAndIsActiveTrue(dto.getBairro())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Bairro não encontrado: " + dto.getBairro()
                ));

        perfilService.criarPerfilCliente(usuario, dto.getSexo(), bairro);

        return defaultUserMapper.response(usuario);
    }

    /**
     * Adiciona o perfil de administrador a um utilizador já existente.
     */

    @Transactional
    public DefaultUserResponseDto adicionarPerfilAdmin(AdminRequestDto dto) {
        DefaultUserModel usuario = defaultUserService.buscarModeloPorId(dto.getUsuario());

        perfilService.criarPerfilAdmin(usuario);

        return defaultUserMapper.response(usuario);
    }

    /**
     * Adiciona o perfil de fornecedor a um utilizador já existente.
     * Exige ROLE_ADMIN no controller.
     */
    @Transactional
    public DefaultUserResponseDto adicionarPerfilFornecedor(RegistoFornecedorRequestDto dto) {
        DefaultUserModel usuario = defaultUserService.buscarModeloPorId(dto.getUsuarioId());

        BairroModel bairro = bairrosRepository.findByIdAndIsActiveTrue(dto.getBairroId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Bairro não encontrado: " + dto.getBairroId()
                ));

        DocumentoDeIdentificacaoModel documento = documentoDeIdentificacaoService
                .criarDocumentoDeIdentificacao(dto.getDocumentoDeIdentificacao());

        perfilService.criarPerfilFornecedor(usuario, dto.getSexo(), bairro, documento);

        return defaultUserMapper.response(usuario);
    }

    /**
     * Adiciona o perfil de gestor a um utilizador já existente.
     * Exige ROLE_ADMIN no controller.
     */
    @Transactional
    public DefaultUserResponseDto adicionarPerfilGestor(GestorRequestDto dto) {
        DefaultUserModel usuario = defaultUserService.buscarModeloPorId(dto.getUsuario());

        DocumentoDeIdentificacaoModel documento = documentoDeIdentificacaoService
                .criarDocumentoDeIdentificacao(dto.getDocumentoDeIdentificacao());

        perfilService.criarPerfilGestor(usuario, dto.getNumeroDoFuncionario(), documento);

        return defaultUserMapper.response(usuario);
    }

    /**
     * Troca o perfil activo do utilizador, exigindo que tenha a role respectiva.
     */
    @Transactional
    public DefaultUserResponseDto trocarPerfil(UUID usuarioId, TipoPerfilEnum tipo) {
        DefaultUserModel usuario = defaultUserService.buscarModeloPorId(usuarioId);

        perfilService.trocarPerfil(usuario, tipo);

        return defaultUserMapper.response(usuario);
    }

    @Transactional(readOnly = true)
    public boolean temRegistoDePerfil(UUID usuarioId, TipoPerfilEnum tipo) {
        DefaultUserModel usuario = defaultUserService.buscarModeloPorId(usuarioId);

        return perfilService.temRegistoDePerfil(usuario, tipo);
    }
}
