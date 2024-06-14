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
@Table(name = "cliente", uniqueConstraints = @UniqueConstraint(columnNames = {"cpf"}))
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
    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/[0-9]{4}$", message = "Formato inserido incorreto. Por favor, utilize o formato DD/MM/AAAA")
    private String dataNascimento;

    @NotBlank(message = "Telefone não pode ser vazio")
    @Column(nullable = false)
    @Pattern(regexp = "^\\(?\\d{2}\\)?[\\s-]?(\\d{4,5})-?(\\d{4})$", message = "Formato inserido incorreto")
    private String telefone;

    @NotBlank(message = "Endereço não pode ser vazio")
    @Column(nullable = false)
    @Size(min = 5, max = 255)
    private String endereco;

    @NotBlank(message = "Renda mensal não pode ser vazia")
    @Column(nullable = false)
    @Pattern(regexp = "^\\d+(,\\d{2})?$", message = "Renda informada não é válida. Por favor, insira um valor numérico válido")
    private String rendaMensal;

    @NotBlank(message = "E-mail não pode ser vazio")
    @Column(nullable = false)
    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "E-mail inválido")
    private String email;

    @NotBlank(message = "Senha não pode ser vazia")
    @Column(nullable = false)
    @Size(min = 8, max = 100)
    @Pattern(regexp = "^(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "Por favor, utilize ao menos um numeral na sua senha")
    private String senha;

    private boolean logado = false;

    private String conta;

    private String saldo = "0";

    private String limite;

    private boolean chequeEspecial;

    private String pix = "";

    private int saquesFeitos = 0;

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

    public boolean isLogado() {
        return logado;
    }

    public void setLogado(boolean logado) {
        this.logado = logado;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
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

    public boolean isChequeEspecial() {
        return chequeEspecial;
    }

    public void setChequeEspecial(boolean chequeEspecial) {
        this.chequeEspecial = chequeEspecial;
    }

    public String getPix() {
        return pix;
    }

    public void setPix(String pix) {
        this.pix = pix;
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
            setChequeEspecial(true);
        }
        else {
            setConta("contaPagamento");
            setChequeEspecial(false);
        }
        atualizarLimite();
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

    public void atualizarLimite(){
        if (getConta().equals("contaCorrente")){
            setLimite(String.valueOf(Double.parseDouble(getSaldo()) + (0.1 * Double.parseDouble(getRendaMensal()))));
        }
        else {
            setLimite(getSaldo());
        }
    }
}
