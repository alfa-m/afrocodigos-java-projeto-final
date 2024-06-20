package com.ficticio.bancoficticio.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transacao")
public class Transacao {
    @Id
    @UuidGenerator
    private UUID idTransacao;

    private UUID idConta;

    private String tipo;

    private double quantia;

    private UUID idContaDestino;

    @CreationTimestamp
    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dataTransacao;

    protected Transacao(){}

    public Transacao(UUID idConta, String tipo, double quantia) {
        this.idConta = idConta;
        this.tipo = tipo;
        this.quantia = quantia;
    }

    public Transacao(UUID idConta, String tipo, double quantia, UUID idContaDestino) {
        this.idConta = idConta;
        this.tipo = tipo;
        this.quantia = quantia;
        this.idContaDestino = idContaDestino;
    }

    public UUID getIdTransacao() {
        return idTransacao;
    }

    public void setIdTransacao(UUID idTransacao) {
        this.idTransacao = idTransacao;
    }

    public UUID getIdConta() {
        return idConta;
    }

    public void setIdConta(UUID idConta) {
        this.idConta = idConta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getQuantia() {
        return quantia;
    }

    public void setQuantia(double quantia) {
        this.quantia = quantia;
    }

    public UUID getIdContaDestino() {
        return idContaDestino;
    }

    public void setIdContaDestino(UUID idContaDestino) {
        this.idContaDestino = idContaDestino;
    }

    public LocalDateTime getDataTransacao() {
        return dataTransacao;
    }

    public void setDataTransacao(LocalDateTime dataTransacao) {
        this.dataTransacao = dataTransacao;
    }
}
