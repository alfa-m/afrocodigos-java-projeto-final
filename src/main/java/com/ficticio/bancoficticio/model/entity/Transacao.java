package com.ficticio.bancoficticio.model.entity;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "transacao")
public class Transacao {
    @Id
    @UuidGenerator
    private UUID idTransacao;
    private UUID idCliente;
    private String tipo;
    private String data;
    private String quantia;

    protected Transacao(){}

    public Transacao(UUID idCliente, String tipo, String data, String quantia) {
        this.idCliente = idCliente;
        this.tipo = tipo;
        this.data = data;
        this.quantia = quantia;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getQuantia() {
        return quantia;
    }

    public void setQuantia(String quantia) {
        this.quantia = quantia;
    }
}
