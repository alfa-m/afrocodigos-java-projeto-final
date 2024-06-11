package com.ficticio.bancoficticio.utils;

public interface InterfaceCliente {
    String getCpf();
    void setCpf(String cpf);
    String getNome();
    void setNome(String nome);
    String getEmail();
    void setEmail(String email);
    String getTelefone();
    void setTelefone(String telefone);
    String getEndereco();
    void setEndereco(String endereco);
    double getRendaMensal();
    void setRendaMensal(double rendaMensal);
    String getSenha();
    void setSenha(String senha);
}
