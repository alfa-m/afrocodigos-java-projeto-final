package com.ficticio.bancoficticio.repository;

import com.ficticio.bancoficticio.model.entity.Cliente;
import com.ficticio.bancoficticio.model.entity.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContaRepository extends JpaRepository <Conta, UUID> {
    Conta findByCliente(Cliente cliente);
}
