package com.ficticio.bancoficticio.model.entity;

import com.ficticio.bancoficticio.config.Cliente;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "cliente-conta-corrente")
public class ContaCorrente extends Cliente {
    public ContaCorrente(String cpf, String nome, String email, String telefone, String endereco, double rendaMensal, String senha) {
        super(cpf, nome, email, telefone, endereco, rendaMensal, senha);
    }
}
