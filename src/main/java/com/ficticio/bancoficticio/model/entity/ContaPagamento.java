package com.ficticio.bancoficticio.model.entity;

import jakarta.persistence.Entity;

@Entity
public class ContaPagamento extends Conta{
    public ContaPagamento() {
    }

    public ContaPagamento(Cliente cliente) {
        super(cliente);
        limite = saldo;
        transferenciaMaxima = 4999.99;
    }
}
