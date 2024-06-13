package com.ficticio.bancoficticio.model.entity;

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
    private UUID idCliente;
    private String tipo;
    @CreationTimestamp
    @Column(updatable = false)
    private String dataTransacao;
    private String quantia;
    private UUID idClienteDestino;

    protected Transacao(){}

    public Transacao(UUID idCliente, String tipo, String quantia) {
        this.idCliente = idCliente;
        this.tipo = tipo;
        this.quantia = quantia;
    }

    public Transacao(UUID idCliente, String tipo, String quantia, UUID idClienteDestino) {
        this.idCliente = idCliente;
        this.tipo = tipo;
        this.quantia = quantia;
        this.idClienteDestino = idClienteDestino;
    }

    public UUID getIdTransacao() {
        return idTransacao;
    }

    public void setIdTransacao(UUID idTransacao) {
        this.idTransacao = idTransacao;
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

    public String getDataTransacao() {
        return dataTransacao;
    }

    public void setDataTransacao(String dataTransacao) {
        this.dataTransacao = dataTransacao;
    }

    public String getQuantia() {
        return quantia;
    }

    public void setQuantia(String quantia) {
        this.quantia = quantia;
    }

    public UUID getIdClienteDestino() {
        return idClienteDestino;
    }

    public void setIdClienteDestino(UUID idClienteDestino) {
        this.idClienteDestino = idClienteDestino;
    }
}
