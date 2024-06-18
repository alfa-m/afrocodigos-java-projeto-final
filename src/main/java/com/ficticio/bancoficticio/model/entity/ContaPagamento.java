package com.ficticio.bancoficticio.model.entity;

import jakarta.persistence.Entity;

@Entity
public class ContaPagamento extends Conta{
    public ContaPagamento() {
    }

    public ContaPagamento(Cliente cliente) {
        super(cliente);
        tipo = "ContaPagamento";
        rendaMensal = cliente.getRendaMensal();
        limite = saldo;
    }
}
