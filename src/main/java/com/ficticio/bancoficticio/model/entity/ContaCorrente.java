package com.ficticio.bancoficticio.model.entity;

import java.util.UUID;

public class ContaCorrente extends Conta {
    private Double chequeEspecial;

    public ContaCorrente(UUID idCliente, String rendaMensal) {
        super(idCliente, rendaMensal);
        this.tipo = "contaCorrente";
        this.chequeEspecial = 0.1 * Double.parseDouble(rendaMensal);
        this.limite = String.valueOf(Double.parseDouble(getSaldo() + chequeEspecial));
    }
}
