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
        saldo = "0";
        limite = String.valueOf(Double.parseDouble(saldo) + (0.1 * Double.parseDouble(rendaMensal)));
    }
}

