package com.api.kwanza_services.Service.User;

import com.api.kwanza_services.Enums.SexoEnum;
import com.api.kwanza_services.Enums.TipoPerfilEnum;
import com.api.kwanza_services.Model.Localizacao.BairroModel;
import com.api.kwanza_services.Model.User.AdminModel;
import com.api.kwanza_services.Model.User.ClienteModel;
import com.api.kwanza_services.Model.User.DefaultUserModel;
import com.api.kwanza_services.Model.User.DocumentoDeIdentificacaoModel;
import com.api.kwanza_services.Model.User.FornecedorModel;
import com.api.kwanza_services.Model.User.GestorModel;
import com.api.kwanza_services.Repository.User.AdminRepository;
import com.api.kwanza_services.Repository.User.ClienteRepository;
import com.api.kwanza_services.Repository.User.DefaultUserRepository;
import com.api.kwanza_services.Repository.User.FornecedorRepository;
import com.api.kwanza_services.Repository.User.GestorRepository;
import com.api.kwanza_services.Service.Roles_Permisisons.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Cria os registos de perfil (cliente, fornecedor, gestor, admin)
 */

@Service
@RequiredArgsConstructor
public class PerfilService {

    private final DefaultUserRepository defaultUserRepository;
    private final ClienteRepository clienteRepository;
    private final FornecedorRepository fornecedorRepository;
    private final GestorRepository gestorRepository;
    private final AdminRepository adminRepository;
    private final RoleService roleService;

    /**
     * Troca o perfil activo, exigindo que o utilizador tenha a role respectiva.
     */
    @Transactional
    public void trocarPerfil(DefaultUserModel usuario, TipoPerfilEnum tipo) {
        if (!roleService.temRole(usuario, tipo)) {
            throw new IllegalArgumentException("O utilizador não tem o perfil " + tipo);
        }

        usuario.setPerfilAtivo(tipo);
        defaultUserRepository.save(usuario);
    }

    @Transactional
    public ClienteModel criarPerfilCliente(DefaultUserModel usuario, SexoEnum sexo, BairroModel bairro) {
        ClienteModel cliente = clienteRepository.findByUsuarioId(usuario.getId())
                .orElseGet(ClienteModel::new);

        cliente.setUsuario(usuario);
        cliente.setSexo(sexo);
        cliente.setBairro(bairro);

        ClienteModel salvo = clienteRepository.save(cliente);
        registarPerfil(usuario, TipoPerfilEnum.CLIENTE);
        return salvo;
    }

    @Transactional
    public FornecedorModel criarPerfilFornecedor(DefaultUserModel usuario,
                                                 SexoEnum sexo,
                                                 BairroModel bairro,
                                                 DocumentoDeIdentificacaoModel documento) {
        FornecedorModel fornecedor = fornecedorRepository.findByUsuarioId(usuario.getId())
                .orElseGet(FornecedorModel::new);

        fornecedor.setUsuario(usuario);
        fornecedor.setSexo(sexo);
        fornecedor.setBairro(bairro);
        fornecedor.setDocumentoDeIdentificacao(documento);

        FornecedorModel salvo = fornecedorRepository.save(fornecedor);
        registarPerfil(usuario, TipoPerfilEnum.FORNECEDOR);
        return salvo;
    }

    @Transactional
    public GestorModel criarPerfilGestor(DefaultUserModel usuario,
                                         String numeroDoFuncionario,
                                         DocumentoDeIdentificacaoModel documento) {
        GestorModel gestor = gestorRepository.findByUsuarioId(usuario.getId())
                .orElseGet(GestorModel::new);

        gestor.setUsuario(usuario);
        gestor.setNumeroDoFuncionario(numeroDoFuncionario);
        gestor.setDocumentoDeIdentificacao(documento);

        GestorModel salvo = gestorRepository.save(gestor);
        registarPerfil(usuario, TipoPerfilEnum.GESTOR);
        return salvo;
    }

    @Transactional
    public AdminModel criarPerfilAdmin(DefaultUserModel usuario) {
        AdminModel admin = adminRepository.findByUsuarioId(usuario.getId())
                .orElseGet(AdminModel::new);

        admin.setUsuario(usuario);

        AdminModel salvo = adminRepository.save(admin);
        registarPerfil(usuario, TipoPerfilEnum.ADMIN);
        return salvo;
    }

    public boolean temRegistoDePerfil(DefaultUserModel usuario, TipoPerfilEnum tipo) {
        UUID id = usuario.getId();

        return switch (tipo) {
            case CLIENTE -> clienteRepository.findByUsuarioId(id).isPresent();
            case FORNECEDOR -> fornecedorRepository.findByUsuarioId(id).isPresent();
            case GESTOR -> gestorRepository.findByUsuarioId(id).isPresent();
            case ADMIN -> adminRepository.findByUsuarioId(id).isPresent();
        };
    }

    /**
     * Atribui a role do perfil e, na primeira vez, define-o como perfil activo.
     */
    private void registarPerfil(DefaultUserModel usuario, TipoPerfilEnum tipo) {
        boolean perfilAtivoEmFalta = usuario.getPerfilAtivo() == null;

        if (perfilAtivoEmFalta) {
            usuario.setPerfilAtivo(tipo);
        }

        boolean roleAdicionada = roleService.atribuirRole(usuario, tipo);


        if (perfilAtivoEmFalta && !roleAdicionada) {
            defaultUserRepository.save(usuario);
        }
    }
}
