package com.ficticio.bancoficticio.model.entity;

import jakarta.persistence.Entity;

@Entity
public class ContaCorrente extends Conta {
    public ContaCorrente() {
    }

    public ContaCorrente(Cliente cliente) {
        super(cliente);
        tipo = "Conta Corrente";
        limite = saldo + (0.1 * cliente.getRendaMensal());
    }

    @Override
    public void atualizaLimite() {
        Cliente cliente = getCliente();
        setLimite(saldo + (0.1 * cliente.getRendaMensal()));
    }
}

