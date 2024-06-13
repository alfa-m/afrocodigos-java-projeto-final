package com.ficticio.bancoficticio.repository;

import com.ficticio.bancoficticio.model.entity.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransacaoRepository extends JpaRepository<Transacao, UUID> {
}
