package com.ficticio.bancoficticio.model.entity;

import com.ficticio.bancoficticio.utils.InterfaceCliente;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "cliente")
public class Cliente implements InterfaceCliente {
    @Id
    @UuidGenerator
    private UUID id;
    private String cpf;
    private String nome;
    private String email;
    private String telefone;
    private String endereco;
    private String rendaMensal = "0";
    private String senha;

    protected Cliente(){}

    public Cliente(String cpf, String nome, String email, String telefone, String endereco, String rendaMensal, String senha) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.rendaMensal = rendaMensal;
        this.senha = senha;
        System.out.println("Cliente criado com sucesso!");
    }

    @Override
    public String getCpf() {
        return cpf;
    }

    @Override
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getTelefone() {
        return telefone;
    }

    @Override
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String getEndereco() {
        return endereco;
    }

    @Override
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public String getRendaMensal() {
        return rendaMensal;
    }

    @Override
    public void setRendaMensal(String rendaMensal) {
        this.rendaMensal = rendaMensal;
    }

    @Override
    public String getSenha() {
        return senha;
    }

    @Override
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void recuperarSenha(String senha){
        this.senha = senha;
    }

    /*
    public void atualizarCadastro(String email, String telefone, String endereco, double rendaMensal, String senha) {
        setEmail(email);
        setTelefone(telefone);
        setEndereco(endereco);
        setRendaMensal(rendaMensal);
        recuperarSenha(senha);
        System.out.println("Cadastro atualizado com sucesso!");
    }
    */

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
