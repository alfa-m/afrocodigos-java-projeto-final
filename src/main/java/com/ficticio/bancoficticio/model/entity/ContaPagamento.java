package com.ficticio.bancoficticio.model.entity;

import jakarta.persistence.Entity;

@Entity
public class ContaPagamento extends Conta{
    public ContaPagamento() {
    }

    public ContaPagamento(Cliente cliente) {
        super(cliente);
        tipo = "Conta Pagamento";
        limite = saldo;
        transferenciaMaxima = 4999.99;
    }

    @Override
    public void atualizaLimite() {
        setLimite(saldo);
    }
}
