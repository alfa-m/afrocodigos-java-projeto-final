package com.ficticio.bancoficticio.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "cliente")
        //, uniqueConstraints = @UniqueConstraint(columnNames = {"cpf"}))
public class Cliente {
    @Id
    @UuidGenerator
    private UUID id;

    @NotBlank(message = "CPF não pode ser vazio")
    @Column(nullable = false)
    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "Formato inserido incorreto. Por favor, utilize o formato XXX.XXX.XXX-XX")
    private String cpf;

    @NotBlank(message = "Nome não pode ser vazio")
    @Column(nullable = false)
    @Size(min = 2, max = 255)
    private String nome;

    @NotBlank(message = "Data de nascimento não pode ser vazio")
    @Column(nullable = false)
    //@Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/[0-9]{4}$", message = "Formato inserido incorreto. Por favor, utilize o formato DD/MM/AAAA")
    private String dataNascimento;

    @NotBlank(message = "Telefone não pode ser vazio")
    @Column(nullable = false)
    //@Pattern(regexp = "^\\(?\\d{2}\\)?[\\s-]?(\\d{4,5})-?(\\d{4})$", message = "Formato inserido incorreto")
    private String telefone;

    @NotBlank(message = "Endereço não pode ser vazio")
    @Column(nullable = false)
    @Size(min = 5, max = 255)
    private String endereco;

    @NotBlank(message = "Renda mensal não pode ser vazia")
    @Column(nullable = false)
    //@Pattern(regexp = "^\\d+(,\\d{2})?$", message = "Renda informada não é válida. Por favor, insira um valor numérico válido")
    private String rendaMensal;

    @NotBlank(message = "E-mail não pode ser vazio")
    @Column(nullable = false)
    //@Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "E-mail inválido")
    private String email;

    @NotBlank(message = "Senha não pode ser vazia")
    @Column(nullable = false)
    @Size(min = 8, max = 100)
    //@Pattern(regexp = "^(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "Por favor, utilize ao menos um numeral na sua senha")
    private String senha;

    private boolean logado = false;


    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    private LocalDateTime dataAtualizacao;

    protected Cliente(){}

    public Cliente(String cpf, String nome, String dataNascimento, String telefone, String endereco, String rendaMensal, String email, String senha) {
        this.cpf = cpf;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.endereco = endereco;
        this.rendaMensal = rendaMensal;
        this.email = email;
        this.senha = senha;
        System.out.println("Cliente criado com sucesso!");
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public @NotBlank(message = "CPF não pode ser vazio") @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "Formato inserido incorreto. Por favor, utilize o formato XXX.XXX.XXX-XX") String getCpf() {
        return cpf;
    }

    public void setCpf(@NotBlank(message = "CPF não pode ser vazio") @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "Formato inserido incorreto. Por favor, utilize o formato XXX.XXX.XXX-XX") String cpf) {
        this.cpf = cpf;
    }

    public @NotBlank(message = "Nome não pode ser vazio") @Size(min = 2, max = 255) String getNome() {
        return nome;
    }

    public void setNome(@NotBlank(message = "Nome não pode ser vazio") @Size(min = 2, max = 255) String nome) {
        this.nome = nome;
    }

    public @NotBlank(message = "Data de nascimento não pode ser vazio") String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(@NotBlank(message = "Data de nascimento não pode ser vazio") String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public @NotBlank(message = "Telefone não pode ser vazio") String getTelefone() {
        return telefone;
    }

    public void setTelefone(@NotBlank(message = "Telefone não pode ser vazio") String telefone) {
        this.telefone = telefone;
    }

    public @NotBlank(message = "Endereço não pode ser vazio") @Size(min = 5, max = 255) String getEndereco() {
        return endereco;
    }

    public void setEndereco(@NotBlank(message = "Endereço não pode ser vazio") @Size(min = 5, max = 255) String endereco) {
        this.endereco = endereco;
    }

    public @NotBlank(message = "Renda mensal não pode ser vazia") String getRendaMensal() {
        return rendaMensal;
    }

    public void setRendaMensal(@NotBlank(message = "Renda mensal não pode ser vazia") String rendaMensal) {
        this.rendaMensal = rendaMensal;
    }

    public @NotBlank(message = "E-mail não pode ser vazio") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "E-mail não pode ser vazio") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Senha não pode ser vazia") @Size(min = 8, max = 100) String getSenha() {
        return senha;
    }

    public void setSenha(@NotBlank(message = "Senha não pode ser vazia") @Size(min = 8, max = 100) String senha) {
        this.senha = senha;
    }

    public boolean isLogado() {
        return logado;
    }

    public void setLogado(boolean logado) {
        this.logado = logado;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}
