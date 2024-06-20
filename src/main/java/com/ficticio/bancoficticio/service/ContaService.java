package com.ficticio.bancoficticio.service;

import com.ficticio.bancoficticio.exception.ClienteException;
import com.ficticio.bancoficticio.exception.ContaException;
import com.ficticio.bancoficticio.model.entity.Cliente;
import com.ficticio.bancoficticio.model.entity.Conta;
import com.ficticio.bancoficticio.model.entity.Transacao;
import com.ficticio.bancoficticio.repository.ClienteRepository;
import com.ficticio.bancoficticio.repository.ContaRepository;
import com.ficticio.bancoficticio.repository.TransacaoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ContaService {
    private final ContaRepository contaRepository;
    private final ClienteRepository clienteRepository;
    private final TransacaoRepository transacaoRepository;

    public ContaService(ContaRepository contaRepository, ClienteRepository clienteRepository, TransacaoRepository transacaoRepository) {
        this.contaRepository = contaRepository;
        this.clienteRepository = clienteRepository;
        this.transacaoRepository = transacaoRepository;
    }

    public Conta verConta(UUID id){
        if (clienteRepository.findById(id).isPresent()){
            Cliente cliente = clienteRepository.findById(id).get();
            if (cliente.isLogado()){
                return contaRepository.findByCliente(cliente);
            } else {
                throw new ClienteException.ClienteNaoLogadoException();
            }
        } else {
            throw new ClienteException.ClienteNaoCadastradoException();
        }
    }

    public double verSaldo(UUID id){
        if (contaRepository.findById(id).isPresent()){
            Conta conta = contaRepository.findById(id).get();
            Cliente cliente = conta.getCliente();

            if(cliente.isLogado()){
                return conta.getSaldo();
            } else {
                throw new ClienteException.ClienteNaoLogadoException();
            }
        } else {
            throw new ContaException.ContaNaoExisteException();
        }
    }

    public List<Transacao> verExtrato(UUID id, LocalDateTime dataInicio, LocalDateTime dataFim){
        if (contaRepository.findById(id).isPresent()){
            Conta conta = contaRepository.findById(id).get();
            Cliente cliente = conta.getCliente();

            if(cliente.isLogado()){
                return transacaoRepository.findTransacaosByIdContaIsAndDataTransacaoIsBetween(id, dataInicio, dataFim);
            } else {
                throw new ClienteException.ClienteNaoLogadoException();
            }
        } else {
            throw new ContaException.ContaNaoExisteException();
        }
    }

    public void fazerDeposito(UUID id, double quantia){
        if (contaRepository.findById(id).isPresent()) {
            Conta conta = contaRepository.findById(id).get();

            if (conta.getCliente().isLogado()) {
                conta.setSaldo(conta.getSaldo() + quantia);
                conta.atualizaLimite();
                Transacao transacao = new Transacao(conta.getId(), "depósito", quantia);
                transacaoRepository.save(transacao);
            } else {
                throw new ClienteException.ClienteNaoLogadoException();
            }
        } else {
            throw new ContaException.ContaNaoExisteException();
        }
    }

    public void fazerSaque(UUID id, double quantia){
        if (contaRepository.findById(id).isPresent()) {
            Conta conta = contaRepository.findById(id).get();

            if (conta.getCliente().isLogado()) {
                double taxaSaque = 0.00;
                if (conta.getSaquesFeitos() > 5){
                    taxaSaque = 6.50;
                }

                if (conta.getLimite() >= (quantia + taxaSaque)) {
                    conta.setSaldo(conta.getSaldo() - quantia - taxaSaque);
                    conta.atualizaLimite();
                    conta.setSaquesFeitos(conta.getSaquesFeitos() + 1);
                    Transacao transacao = new Transacao(conta.getId(), "saque", quantia);
                    transacaoRepository.save(transacao);
                } else {
                    throw new ContaException.SemLimiteException();
                }
            } else {
                throw new ClienteException.ClienteNaoLogadoException();
            }
        } else {
            throw new ContaException.ContaNaoExisteException();
        }
    }

    public void fazerPagamento(UUID id, double quantia){
        if (contaRepository.findById(id).isPresent()) {
            Conta conta = contaRepository.findById(id).get();

            if (conta.getCliente().isLogado()) {
                if (conta.getLimite() >= quantia) {
                    conta.setSaldo(conta.getSaldo() - quantia);
                    conta.atualizaLimite();
                    Transacao transacao = new Transacao(conta.getId(), "pagamento de conta", quantia);
                    transacaoRepository.save(transacao);
                } else {
                    throw new ContaException.SemLimiteException();
                }
            } else {
                throw new ClienteException.ClienteNaoLogadoException();
            }
        } else {
            throw new ContaException.ContaNaoExisteException();
        }
    }

    public void fazerTransferencia(UUID idContaOrigem, UUID idContaDestino, double quantia){
        if (contaRepository.findById(idContaOrigem).isPresent() && contaRepository.findById(idContaDestino).isPresent()){
            Conta contaOrigem = contaRepository.findById(idContaOrigem).get();
            Conta contaDestino = contaRepository.findById(idContaDestino).get();

            if (contaOrigem.getCliente().isLogado()){
                if (contaOrigem.getLimite() >= quantia) {
                    if ((contaOrigem.getTipo().equals("Conta Pagamento") && (quantia <= contaOrigem.getTransferenciaMaxima()) || contaOrigem.getTipo().equals("Conta Corrente"))) {
                        contaOrigem.setSaldo(contaOrigem.getSaldo() - quantia);
                        contaOrigem.atualizaLimite();

                        contaDestino.setSaldo(contaDestino.getSaldo() + quantia);
                        contaDestino.atualizaLimite();

                        Transacao transacao = new Transacao(contaOrigem.getId(), "transferência bancária", quantia, contaDestino.getId());
                        transacaoRepository.save(transacao);
                    } else {
                    throw new ContaException.ContaSemPermissaoException();
                    }
                } else {
                    throw new ContaException.SemLimiteException();
                }
            } else {
                throw new ClienteException.ClienteNaoLogadoException();
            }
        } else {
            throw new ContaException.ContaNaoExisteException();
        }
    }

    public void fazerPix(UUID id, String chavePix, double quantia){
        if (contaRepository.findById(id).isPresent() && (contaRepository.findByChavePix(chavePix) != null)) {
            Conta contaOrigem = contaRepository.findById(id).get();
            Conta contaDestino = contaRepository.findByChavePix(chavePix);

            if (contaOrigem.getChavePix() != null){
                if (contaOrigem.getCliente().isLogado()) {
                    if (contaOrigem.getLimite() >= quantia) {
                        contaOrigem.setSaldo(contaOrigem.getSaldo() - quantia);
                        contaOrigem.atualizaLimite();

                        contaDestino.setSaldo(contaDestino.getSaldo() + quantia);
                        contaDestino.atualizaLimite();

                        Transacao transacao = new Transacao(contaOrigem.getId(), "pix", quantia, contaDestino.getId());
                        transacaoRepository.save(transacao);
                    } else {
                        throw new ContaException.SemLimiteException();
                    }
                } else {
                    throw new ClienteException.ClienteNaoLogadoException();
                }
            } else {
                throw new ContaException.SemChavePixException();
            }
        } else {
            throw new ContaException.ContaNaoExisteException();
        }
    }

    public void cadastraChavePix(UUID id, String chavePix){
        if (contaRepository.findById(id).isPresent()) {
            Conta conta = contaRepository.findById(id).get();

            if (conta.getCliente().isLogado()) {
                conta.setChavePix(chavePix);
                contaRepository.save(conta);
            } else {
                throw new ClienteException.ClienteNaoLogadoException();
            }
        } else {
            throw new ContaException.ContaNaoExisteException();
        }
    }

    public void descadastraChavePix(UUID id){
        if (contaRepository.findById(id).isPresent()) {
            Conta conta = contaRepository.findById(id).get();

            if (conta.getCliente().isLogado()) {
                conta.setChavePix(null);
                contaRepository.save(conta);
            } else {
                throw new ClienteException.ClienteNaoLogadoException();
            }
        } else {
            throw new ContaException.ContaNaoExisteException();
        }
    }
}
