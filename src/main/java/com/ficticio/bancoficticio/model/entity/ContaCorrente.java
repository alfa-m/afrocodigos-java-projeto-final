package com.ficticio.bancoficticio.model.entity;

import jakarta.persistence.Entity;

@Entity
public class ContaCorrente extends Conta {
    public ContaCorrente() {
    }

    public ContaCorrente(Cliente cliente) {
        super(cliente);
        tipo = "ContaCorrente";
        rendaMensal = cliente.getRendaMensal();
        limite = saldo + (0.1 * rendaMensal);
    }
}

