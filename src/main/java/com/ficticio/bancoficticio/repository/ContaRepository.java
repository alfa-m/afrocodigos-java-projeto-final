package com.ficticio.bancoficticio.repository;

import com.ficticio.bancoficticio.model.entity.Cliente;
import com.ficticio.bancoficticio.model.entity.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository <Conta, Long> {
    Conta findByCliente(Cliente cliente);
}
