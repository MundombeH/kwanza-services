package com.api.kwanza_services.Mapper.User;

import com.api.kwanza_services.Dto.DefaultDto;
import com.api.kwanza_services.Dto.Localizacao.Bairro.BairroResumoDto;
import com.api.kwanza_services.Dto.Localizacao.Municipio.MunicipiosResumoDTO;
import com.api.kwanza_services.Dto.Localizacao.Pais.PaisResumoDTO;
import com.api.kwanza_services.Dto.Localizacao.Provincia.ProvinciasResumoDTO;
import com.api.kwanza_services.Dto.Roles_Permissions.Permissions.PermissionResumoDto;
import com.api.kwanza_services.Dto.Roles_Permissions.Roles.RolesResumoDto;
import com.api.kwanza_services.Dto.User.DefaultUser.DefaultUserResponseDto;
import com.api.kwanza_services.Dto.User.DocumentoDeIdentificacao.DocumentoDeIdentificacaoResponseDto;
import com.api.kwanza_services.Dto.User.Fornecedor.FornecedorResponseDto;
import com.api.kwanza_services.Model.DefaultModel;
import com.api.kwanza_services.Model.Localizacao.BairroModel;
import com.api.kwanza_services.Model.Localizacao.MunicipioModel;
import com.api.kwanza_services.Model.Localizacao.PaisModel;
import com.api.kwanza_services.Model.Localizacao.ProvinciasModel;
import com.api.kwanza_services.Model.Roles_Permisisons.PermissionModel;
import com.api.kwanza_services.Model.Roles_Permisisons.RoleModel;
import com.api.kwanza_services.Model.User.DefaultUserModel;
import com.api.kwanza_services.Model.User.DocumentoDeIdentificacaoModel;
import com.api.kwanza_services.Model.User.FornecedorModel;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class FornecedorMapper {

    public FornecedorResponseDto toResponse(FornecedorModel model) {
        FornecedorResponseDto dto = new FornecedorResponseDto();
        fillDefaultFields(dto, model);

        dto.setUsuario(toUserResponse(model.getUsuario()));
        dto.setBairro(toBairroResumo(model.getBairro()));
        dto.setSexo(model.getSexo());
        dto.setIdentidadeValidada(model.getIdentidadeValidada());
        dto.setDocumentoDeIdentificacao(toDocumentoResponse(model.getDocumentoDeIdentificacao()));

        return dto;
    }

    private DefaultUserResponseDto toUserResponse(DefaultUserModel model) {
        if (model == null) {
            return null;
        }

        DefaultUserResponseDto dto = new DefaultUserResponseDto();
        fillDefaultFields(dto, model);

        dto.setNome(model.getNome());
        dto.setNumeroDeTelefone(model.getNumeroDeTelefone());
        dto.setEmail(model.getEmail());
        dto.setPerfilAtivo(model.getPerfilAtivo());
        dto.setRoles(mapRoles(model.getRoles()));

        return dto;
    }

    private Set<RolesResumoDto> mapRoles(Set<RoleModel> roles) {
        if (roles == null) {
            return Set.of();
        }

        return roles.stream()
                .map(this::toRoleResumo)
                .collect(Collectors.toSet());
    }

    private RolesResumoDto toRoleResumo(RoleModel model) {
        RolesResumoDto dto = new RolesResumoDto();
        dto.setId(model.getId());
        dto.setDescricao(model.getDescricao());
        dto.setIsActive(model.getIsActive());
        dto.setPermissions(mapPermissions(model.getPermissions()));
        return dto;
    }

    private Set<PermissionResumoDto> mapPermissions(Set<PermissionModel> permissions) {
        if (permissions == null) {
            return Set.of();
        }

        return permissions.stream()
                .map(this::toPermissionResumo)
                .collect(Collectors.toSet());
    }

    private PermissionResumoDto toPermissionResumo(PermissionModel model) {
        PermissionResumoDto dto = new PermissionResumoDto();
        dto.setId(model.getId());
        dto.setDescricao(model.getDescricao());
        dto.setIsActive(model.getIsActive());
        return dto;
    }

    private BairroResumoDto toBairroResumo(BairroModel model) {
        if (model == null) {
            return null;
        }

        BairroResumoDto dto = new BairroResumoDto();
        dto.setId(model.getId());
        dto.setDescricao(model.getDescricao());
        dto.setIsActive(model.getIsActive());
        dto.setMunicipio(toMunicipioResumo(model.getMunicipio()));
        return dto;
    }

    private MunicipiosResumoDTO toMunicipioResumo(MunicipioModel model) {
        if (model == null) {
            return null;
        }

        MunicipiosResumoDTO dto = new MunicipiosResumoDTO();
        dto.setId(model.getId());
        dto.setDescricao(model.getDescricao());
        dto.setActive(Boolean.TRUE.equals(model.getIsActive()));
        dto.setProvincia(toProvinciaResumo(model.getProvincia()));
        return dto;
    }

    private ProvinciasResumoDTO toProvinciaResumo(ProvinciasModel model) {
        if (model == null) {
            return null;
        }

        ProvinciasResumoDTO dto = new ProvinciasResumoDTO();
        dto.setId(model.getId());
        dto.setDescricao(model.getDescricao());
        dto.setActive(Boolean.TRUE.equals(model.getIsActive()));
        dto.setPais(toPaisResumo(model.getPais()));
        return dto;
    }

    private PaisResumoDTO toPaisResumo(PaisModel model) {
        if (model == null) {
            return null;
        }

        PaisResumoDTO dto = new PaisResumoDTO();
        dto.setId(model.getId());
        dto.setDescricao(model.getDescricao());
        dto.setActive(Boolean.TRUE.equals(model.getIsActive()));
        return dto;
    }

    private DocumentoDeIdentificacaoResponseDto toDocumentoResponse(DocumentoDeIdentificacaoModel model) {
        if (model == null) {
            return null;
        }

        DocumentoDeIdentificacaoResponseDto dto = new DocumentoDeIdentificacaoResponseDto();
        fillDefaultFields(dto, model);

        dto.setNomeCompleto(model.getNomeCompleto());
        dto.setNumeroDoBi(model.getNumeroDoBi());
        dto.setNomeDoPai(model.getNomeDoPai());
        dto.setNomeDaMae(model.getNomeDaMae());
        dto.setResidencia(model.getResidencia());
        dto.setNaturalDe(model.getNaturalDe());
        dto.setBairroDe(model.getBairroDe());
        dto.setProvinciaDe(model.getProvinciaDe());
        dto.setDataDeNascimento(model.getDataDeNascimento());
        dto.setSexo(model.getSexo());
        dto.setEstadoCivil(model.getEstadoCivil());
        dto.setEmitidoEm(model.getEmitidoEm());
        dto.setValidoAte(model.getValidoAte());
        dto.setAltura(model.getAltura());
        dto.setTipoDocumento(model.getTipoDocumento());
        dto.setCodigoDoPais(model.getCodigoDoPais());
        dto.setProfissao(model.getProfissao());
        dto.setNumeroDoPassaporte(model.getNumeroDoPassaporte());
        dto.setNumeroPessoal(model.getNumeroPessoal());

        return dto;
    }

    private void fillDefaultFields(DefaultDto dto, DefaultModel model) {
        dto.setId(model.getId());
        dto.setCreatedAt(model.getCreatedAt());
        dto.setUpdatedAt(model.getUpdatedAt());
        dto.setIsActive(model.getIsActive());
    }
}
