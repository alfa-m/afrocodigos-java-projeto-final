package com.ficticio.bancoficticio.service;

import com.ficticio.bancoficticio.exception.ClienteException;
import com.ficticio.bancoficticio.model.entity.Cliente;
import com.ficticio.bancoficticio.model.entity.Conta;
import com.ficticio.bancoficticio.model.entity.ContaCorrente;
import com.ficticio.bancoficticio.model.entity.ContaPagamento;
import com.ficticio.bancoficticio.repository.ClienteRepository;
import com.ficticio.bancoficticio.repository.ContaRepository;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    private final ContaRepository contaRepository;
    private ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository, ContaRepository contaRepository) {
        this.clienteRepository = clienteRepository;
        this.contaRepository = contaRepository;
    }

    public Cliente cadastrarCliente(Cliente cliente) {
        boolean clienteJaCadastrado = clienteRepository.existsByNomeAndCpf(cliente.getNome(), cliente.getCpf());

        if (clienteJaCadastrado) {
            throw new ClienteException.ClienteJaCadastradoException();
        }

        return clienteRepository.save(cliente);
    }

    public Conta selecionarConta(Cliente cliente) {
        if (Double.parseDouble(cliente.getRendaMensal()) >= 2900.00) {
            return new ContaCorrente(cliente);
        } else {
            return new ContaPagamento(cliente);
        }
    }

    public void descadastrarConta(Cliente cliente) {
        Conta conta = contaRepository.findByCliente(cliente);
        contaRepository.delete(conta);
    }

    public void descadastrarCliente(Cliente cliente) {
        if (clienteRepository.findById(cliente.getId()).isPresent()) {
            if (cliente.isLogado()) {
                descadastrarConta(cliente);
                clienteRepository.deleteById(cliente.getId());
            } else {
                throw new ClienteException.ClienteNaoLogado();
            }
        } else {
            throw new ClienteException.ClienteNaoCadastradoException();
        }
    }

    public void logarCliente(Cliente cliente) {
        if (clienteRepository.findById(cliente.getId()).isPresent()) {
            cliente.setLogado(true);
        } else {
            throw new ClienteException.ClienteNaoCadastradoException();
        }
    }

    public void deslogarCliente(Cliente cliente) {
        if (clienteRepository.findById(cliente.getId()).isPresent()) {
            cliente.setLogado(false);
        } else {
            throw new ClienteException.ClienteNaoCadastradoException();
        }
    }

}
