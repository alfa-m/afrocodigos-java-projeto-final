package com.ficticio.bancoficticio.model.entity;

import jakarta.persistence.Column;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Conta {
    private UUID idCliente;

    protected String tipo;

    private String rendaMensal;

    private String saldo = "0";

    protected String limite;

    private String chavePix;

    private int saquesFeitos;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime dataCriacaoConta;

    @UpdateTimestamp
    private LocalDateTime dataAtualizacaoConta;

    protected Conta(){}

    public Conta(UUID idCliente) {
        this.idCliente = idCliente;
    }

    public UUID getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(UUID idCliente) {
        this.idCliente = idCliente;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getRendaMensal() {
        return rendaMensal;
    }

    public void setRendaMensal(String rendaMensal) {
        this.rendaMensal = rendaMensal;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getLimite() {
        return limite;
    }

    public void setLimite(String limite) {
        this.limite = limite;
    }

    public String getChavePix() {
        return chavePix;
    }

    public void setChavePix(String chavePix) {
        this.chavePix = chavePix;
    }

    public int getSaquesFeitos() {
        return saquesFeitos;
    }

    public void setSaquesFeitos(int saquesFeitos) {
        this.saquesFeitos = saquesFeitos;
    }

    public LocalDateTime getDataCriacaoConta() {
        return dataCriacaoConta;
    }

    public void setDataCriacaoConta(LocalDateTime dataCriacaoConta) {
        this.dataCriacaoConta = dataCriacaoConta;
    }

    public LocalDateTime getDataAtualizacaoConta() {
        return dataAtualizacaoConta;
    }

    public void setDataAtualizacaoConta(LocalDateTime dataAtualizacaoConta) {
        this.dataAtualizacaoConta = dataAtualizacaoConta;
    }
}
