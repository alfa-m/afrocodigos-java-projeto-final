package com.ficticio.bancoficticio.service;

import com.ficticio.bancoficticio.exception.ClienteException;
import com.ficticio.bancoficticio.model.entity.Cliente;
import com.ficticio.bancoficticio.model.entity.Conta;
import com.ficticio.bancoficticio.model.entity.ContaCorrente;
import com.ficticio.bancoficticio.model.entity.ContaPagamento;
import com.ficticio.bancoficticio.repository.ClienteRepository;
import com.ficticio.bancoficticio.repository.ContaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
        if (cliente.getRendaMensal() >= 2900.00) {
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
                throw new ClienteException.ClienteNaoLogadoException();
            }
        } else {
            throw new ClienteException.ClienteNaoCadastradoException();
        }
    }

    public void atualizarCliente(UUID id, Cliente cliente) {
        if (clienteRepository.findById(id).isPresent()) {
            if (cliente.isLogado()) {
                Cliente clienteAtualizado = cliente;
                clienteAtualizado.setTelefone(cliente.getTelefone());
                clienteAtualizado.setEndereco(cliente.getEndereco());
                clienteAtualizado.setRendaMensal(cliente.getRendaMensal());
                clienteAtualizado.setEmail(cliente.getEmail());
                clienteAtualizado.setSenha(cliente.getSenha());

                if (clienteAtualizado.getRendaMensal() >= 2900.00) {
                    ofertarUpgradeConta(clienteAtualizado);
                }

                clienteRepository.save(clienteAtualizado);
            } else {
                throw new ClienteException.ClienteNaoLogadoException();
            }
        } else {
            throw new ClienteException.ClienteNaoCadastradoException();
        }
    }

    public void redefinirSenha(Cliente cliente, String senha) {
        if (clienteRepository.findById(cliente.getId()).isPresent()) {
            cliente.setSenha(senha);
            clienteRepository.save(cliente);
        } else {
            throw new ClienteException.ClienteNaoCadastradoException();
        }
    }

    public void ofertarUpgradeConta(Cliente cliente) {
        System.out.println("Você gostaria de atualizar sua conta para a categoria Conta Corrente?");
        System.out.println("Acesse /upgrade-de-conta para fazer a atualização!");
    }

    public void upgradeConta(UUID id) {
        if (clienteRepository.findById(id).isPresent()) {
            Cliente cliente = clienteRepository.findById(id).get();
            if (cliente.isLogado()) {
                if (cliente.getRendaMensal() < 2900.00) {
                    Conta contaAntiga = contaRepository.findByCliente(cliente);
                    contaRepository.delete(contaAntiga);
                    Conta contaNova = new ContaCorrente(cliente);
                    contaRepository.save(contaNova);
                } else {
                    throw new ClienteException.RendaBaixaException();
                }
            } else {
                throw new ClienteException.ClienteNaoLogadoException();
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
