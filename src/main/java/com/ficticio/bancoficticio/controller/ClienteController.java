package com.ficticio.bancoficticio.controller;

import com.ficticio.bancoficticio.config.Cliente;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClienteController {
    Cliente cliente1 = new Cliente("123.456.789-00", "Cliente 1", "cliente1@email.com", "1234-5678", "Endereço do cliente, numero", 1800.00, "senha123");

    @GetMapping("/{CPF}/saldo")
    public void pegaSaldo(@PathVariable String cpf) {
        cliente1.verSaldo();
    }

    @GetMapping("/{CPF}/extrato")
    public void pegaExtrato(@PathVariable String cpf) {
        cliente1.verExtrato();
    }

    @GetMapping("/{CPF}/cadastrar")
    public void fazCadastro(@PathVariable String cpf) {
        Cliente clienteCadastrado = new Cliente("123.456.789-01", "Cliente 2", "cliente2@email.com", "2345-6789", "Endereço do cliente2, numero2", 1800.00, "senha234");
    }

    @GetMapping("/{CPF}/deletar")
    public void deletaCadastro(@PathVariable String cpf) {
        cliente1.encerrarConta();
    }

    @GetMapping("/{CPF}/atualizar")
    public void atualizaCadastro(@PathVariable String cpf) {
        cliente1.atualizarCadastro();
    }

    @GetMapping("/{CPF}/transferir")
    public void fazTransferencia(@PathVariable String cpf) {
        cliente1.realizarTransferencia();
    }

    @GetMapping("/{CPF}/depositar")
    public void fazDeposito(@PathVariable String cpf) {
        cliente1.realizarDeposito();
    }

    @GetMapping("/{CPF}/saque")
    public void fazSaque(@PathVariable String cpf) {
        cliente1.realizarSaque();
    }

    @GetMapping("/{CPF}/pagamento")
    public void fazPagamento(@PathVariable String cpf) {
        cliente1.pagarConta();
    }
}
