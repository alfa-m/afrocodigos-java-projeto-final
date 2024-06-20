package com.ficticio.bancoficticio.repository;

import com.ficticio.bancoficticio.model.entity.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TransacaoRepository extends JpaRepository<Transacao, UUID> {
    List<Transacao> findTransacaosByTipoContainingIgnoreCase(String tipo);
    List<Transacao> findTransacaosByIdContaIsAndDataTransacaoIsBetween(UUID idConta, LocalDateTime dataInicio, LocalDateTime dataFim);
    List<Transacao> findTransacaosByTipoContainingIgnoreCaseAndIdContaIsAndDataTransacaoIsBetween(String tipo, UUID idConta, LocalDateTime dataInicio, LocalDateTime dataFim);
}