package com.api.kwanza_services.Mapper.User;

import com.api.kwanza_services.Dto.User.DocumentoDeIdentificacao.DocumentoDeIdentificacaoResponseDto;
import com.api.kwanza_services.Model.User.DocumentoDeIdentificacaoModel;
import org.springframework.stereotype.Component;


@Component
public class DocumentoDeIdentificacaoMapper {

    public DocumentoDeIdentificacaoResponseDto buscar(DocumentoDeIdentificacaoModel model) {
        if (model == null) {
            return null;
        }

        DocumentoDeIdentificacaoResponseDto dto = new DocumentoDeIdentificacaoResponseDto();

        dto.setId(model.getId());
        dto.setCreatedAt(model.getCreatedAt());
        dto.setIsActive(model.getIsActive());
        dto.setUpdatedAt(model.getUpdatedAt());

        dto.setNomeCompleto(model.getNomeCompleto());
        dto.setNumeroDoBi(model.getNumeroDoBi());
        dto.setNomeDoPai(model.getNomeDoPai());
        dto.setNomeDaMae(model.getNomeDaMae());
        dto.setBairroDe(model.getBairroDe());
        dto.setProvinciaDe(model.getProvinciaDe());
        dto.setResidencia(model.getResidencia());
        dto.setNaturalDe(model.getNaturalDe());
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
}
