package com.api.kwanza_services.Service.User;

import com.api.kwanza_services.Dto.User.DocumentoDeIdentificacao.DocumentoDeIdentificacaoRequestDto;
import com.api.kwanza_services.Dto.User.DocumentoDeIdentificacao.DocumentoDeIdentificacaoResponseDto;
import com.api.kwanza_services.Exception.RecursoNaoEncontradoException;
import com.api.kwanza_services.Mapper.User.DocumentoDeIdentificacaoMapper;
import com.api.kwanza_services.Model.User.DocumentoDeIdentificacaoModel;
import com.api.kwanza_services.Repository.User.DocumentoDeIdentificacaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DocumentoDeIdentificacaoService {
    private final DocumentoDeIdentificacaoRepository documentoDeIdentificacaoRepository;
    private final DocumentoDeIdentificacaoMapper documentoDeIdentificacaoMapper;

    public DocumentoDeIdentificacaoService(DocumentoDeIdentificacaoRepository documentoDeIdentificacaoRepository, DocumentoDeIdentificacaoMapper documentoDeIdentificacaoMapper) {
        this.documentoDeIdentificacaoRepository = documentoDeIdentificacaoRepository;
        this.documentoDeIdentificacaoMapper = documentoDeIdentificacaoMapper;
    }



//    @Transactional(readOnly = true)
//    public Page<DocumentoDeIdentificacaoResponseDto> GetAllDocumentos(Pageable pageable){
//
//        return documentoDeIdentificacaoMapper.buscar(documentoDeIdentificacaoRepository.findAll(pageable));
//    }


    @Transactional(readOnly = true)
    public DocumentoDeIdentificacaoResponseDto GetAllDocumentos(UUID id){
    DocumentoDeIdentificacaoModel model = documentoDeIdentificacaoRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("DOcumento nao encontrado "+ id));

        return documentoDeIdentificacaoMapper.buscar(model);
    }


    @Transactional
    public DocumentoDeIdentificacaoModel criarDocumentoDeIdentificacao(DocumentoDeIdentificacaoRequestDto dto){
        DocumentoDeIdentificacaoModel documentoModel= new DocumentoDeIdentificacaoModel();

        documentoModel.setNomeCompleto(dto.getNomeCompleto());
        documentoModel.setNumeroDoBi(dto.getNumeroDoBi());
        documentoModel.setNomeDoPai(dto.getNomeDoPai());
        documentoModel.setNomeDaMae(dto.getNomeDaMae());
        documentoModel.setBairroDe(dto.getBairroDe());
        documentoModel.setProvinciaDe(dto.getProvinciaDe());
        documentoModel.setAltura(dto.getAltura());
        documentoModel.setCodigoDoPais(dto.getCodigoDoPais());
        documentoModel.setDataDeNascimento(dto.getDataDeNascimento());
        documentoModel.setEmitidoEm(dto.getEmitidoEm());
        documentoModel.setEstadoCivil(dto.getEstadoCivil());
        documentoModel.setValidoAte(dto.getValidoAte());
        documentoModel.setTipoDocumento(dto.getTipoDocumento());
        documentoModel.setSexo(dto.getSexo());
        documentoModel.setEstadoCivil(dto.getEstadoCivil());
        documentoModel.setResidencia(dto.getResidencia());
        documentoModel.setNaturalDe(dto.getNaturalDe());
        documentoModel.setProfissao(dto.getProfissao());
        documentoModel.setNumeroPessoal(dto.getNumeroPessoal());
        documentoModel.setNumeroDoPassaporte(dto.getNumeroDoPassaporte());


        return documentoDeIdentificacaoRepository.save(documentoModel);


    }

}
