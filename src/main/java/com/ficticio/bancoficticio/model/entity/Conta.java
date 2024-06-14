package com.ficticio.bancoficticio.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Embeddable
public abstract class Conta {
    private UUID idCliente;

    private String tipo;

    private String saldo = "0";

    private String limite;

    private String chavePix;

    private int saquesFeitos;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime dataCriacaoConta;

    @UpdateTimestamp
    private LocalDateTime dataAtualizacaoConta;

    public Conta(UUID idCliente) {
        this.idCliente = idCliente;
    }
}
