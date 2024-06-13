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
    private String pix = "";
    private String conta;
    private boolean logado = false;
    private int saquesFeitos = 0;

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
        escolheConta(rendaMensal);
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

    public String getPix() {
        return pix;
    }

    public void setPix(String pix) {
        this.pix = pix;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public boolean isLogado() {
        return logado;
    }

    public void setLogado(boolean logado) {
        this.logado = logado;
    }

    public int getSaquesFeitos() {
        return saquesFeitos;
    }

    public void setSaquesFeitos(int saquesFeitos) {
        this.saquesFeitos = saquesFeitos;
    }

    public void realizarLogin(){
        setLogado(true);
    }

    public void realizarLogoff(){
        setLogado(false);
    }

    public void cadastrarPix(String chavePix){
        setPix(chavePix);
    }

    public void descadastrarPix(){
        setPix("");
    }

    public String verSaldo(){
        return getSaldo();
    }

    public String verExtrato(){
        return getSaldo();
    }

    public void escolheConta(String rendaMensal){
        if (Double.parseDouble(rendaMensal) >= 2900.00){
            setConta("contaCorrente");
        }
        else {
            setConta("contaPagamento");
        }
    }

    public void realizarDeposito(String saldoAtualizado){
        setSaldo(saldoAtualizado);
    }

    public void realizarSaque(String saldoAtualizado){
        setSaldo(saldoAtualizado);
    }

    public void pagarConta(String saldoAtualizado){
        setSaldo(saldoAtualizado);
    }

}
