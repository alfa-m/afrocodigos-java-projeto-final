package com.ficticio.bancoficticio.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @UuidGenerator
    private UUID id;
    private String cpf;
    private String nome;
    private String dataNascimento;
    private String telefone;
    private String endereco;
    private String rendaMensal = "0";
    private String email;
    private String senha;
    private String saldo = "0";
    private boolean status = false;

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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getRendaMensal() {
        return rendaMensal;
    }

    public void setRendaMensal(String rendaMensal) {
        this.rendaMensal = rendaMensal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void atualizarCadastro(){
        System.out.println("Cadastro atualizado");
    }

    public void verSaldo(){
        System.out.println("Ver saldo");
    }

    public void verExtrato(){
        System.out.println("Ver extrato");
    }

    public void realizarTransferencia(){
        System.out.println("Fazendo transferência");
    }

    public void realizarDeposito(){
        System.out.println("Fazendo depósito");
    }

    public void realizarSaque(){
        System.out.println("Fazendo saque");
    }

    public void pagarConta(){
        System.out.println("Pagando conta");
    }

    public void encerrarConta(){
        System.out.println("Encerrando conta");
    }
}
