package com.ficticio.bancoficticio.repository;

import com.ficticio.bancoficticio.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {

}
