package com.ficticio.bancoficticio.model.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "cliente")
    private Cliente cliente;

    protected String tipo;

    protected String rendaMensal;

    protected String saldo = "0";

    protected String limite;

    protected String transferenciaMaxima;

    protected String chavePix;

    protected int saquesFeitos = 0;


    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime dataCriacaoConta;

    @UpdateTimestamp
    private LocalDateTime dataAtualizacaoConta;

    protected Conta(){}

    public Conta(Cliente cliente) {
        this.cliente = cliente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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

    public String getTransferenciaMaxima() {
        return transferenciaMaxima;
    }

    public void setTransferenciaMaxima(String transferenciaMaxima) {
        this.transferenciaMaxima = transferenciaMaxima;
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

    public LocalDateTime getDataAtualizacaoConta() {
        return dataAtualizacaoConta;
    }

}
