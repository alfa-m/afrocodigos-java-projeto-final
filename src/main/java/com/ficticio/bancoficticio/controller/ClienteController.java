package com.ficticio.bancoficticio.controller;

import com.ficticio.bancoficticio.config.Cliente;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClienteController {
    Cliente cliente1 = new Cliente("123.456.789-00", "Cliente 1", "cliente1@email.com", "1234-5678", "Endereço do cliente, numero", 1800.00, "senha123");

    @GetMapping("/saldo")
    public void pegaSaldo() {
        cliente1.verSaldo();
    }

    @GetMapping("/extrato")
    public void pegaExtrato() {
        cliente1.verExtrato();
    }

    @GetMapping("/cadastrar")
    public void fazCadastro() {
        Cliente clienteCadastrado = new Cliente("123.456.789-01", "Cliente 2", "cliente2@email.com", "2345-6789", "Endereço do cliente2, numero2", 1800.00, "senha234");
    }

    @GetMapping("/deletar")
    public void deletaCadastro() {
        cliente1.encerrarConta();
    }

    @GetMapping("/atualizar")
    public void atualizaCadastro() {
        cliente1.atualizarCadastro();
    }

    @GetMapping("/transferir")
    public void fazTransferencia() {
        cliente1.realizarTransferencia();
    }

    @GetMapping("/depositar")
    public void fazDeposito() {
        cliente1.realizarDeposito();
    }

    @GetMapping("/saque")
    public void fazSaque() {
        cliente1.realizarSaque();
    }

    @GetMapping("/pagamento")
    public void fazPagamento() {
        cliente1.pagarConta();
    }
}
