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

    protected Double rendaMensal;

    protected Double saldo = 0.0;

    protected Double limite;

    protected Double transferenciaMaxima;

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

    public Double getRendaMensal() {
        return rendaMensal;
    }

    public void setRendaMensal(Double rendaMensal) {
        this.rendaMensal = rendaMensal;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Double getLimite() {
        return limite;
    }

    public void setLimite(Double limite) {
        this.limite = limite;
    }

    public Double getTransferenciaMaxima() {
        return transferenciaMaxima;
    }

    public void setTransferenciaMaxima(Double transferenciaMaxima) {
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
