package com.ficticio.bancoficticio.model.entity;

import com.ficticio.bancoficticio.controller.ClienteController;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.UuidGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@Entity
@Table(name = "cliente")
public class Cliente {
    private static final Logger log = LoggerFactory.getLogger(ClienteController.class);

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

    public void atualizarCadastro(){
        System.out.println("Cadastro atualizado");
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

    public void realizarTransferencia(){
        System.out.println("Fazendo transferência");
    }

    public void realizarDeposito(String saldoAtualizado){
        setSaldo(saldoAtualizado);
        log.info("Depósito realizado com sucesso!");
    }

    public void realizarSaque(String saldoAtualizado){
        setSaldo(saldoAtualizado);
        log.info("Saque realizado com sucesso!");
    }

    public void pagarConta(String saldoAtualizado){
        setSaldo(saldoAtualizado);
        log.info("Pagamento de conta realizado com sucesso!");
    }

}
