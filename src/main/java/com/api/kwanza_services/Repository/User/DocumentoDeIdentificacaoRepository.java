package com.api.kwanza_services.Repository.User;

import com.api.kwanza_services.Model.User.DocumentoDeIdentificacaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DocumentoDeIdentificacaoRepository extends JpaRepository<DocumentoDeIdentificacaoModel, UUID> {
}
