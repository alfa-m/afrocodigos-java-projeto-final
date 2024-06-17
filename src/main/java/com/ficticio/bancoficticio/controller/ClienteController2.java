package com.ficticio.bancoficticio.controller;

import com.ficticio.bancoficticio.model.entity.Cliente;
import com.ficticio.bancoficticio.model.entity.Transacao;
import com.ficticio.bancoficticio.repository.ClienteRepository;
import com.ficticio.bancoficticio.repository.TransacaoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ClienteController2 {
    private final ClienteRepository clienteRepository;
    private final TransacaoRepository transacaoRepository;

    public ClienteController2(ClienteRepository clienteRepository, TransacaoRepository transacaoRepository) {
        this.clienteRepository = clienteRepository;
        this.transacaoRepository = transacaoRepository;
    }

    /*
    @GetMapping("/{id}/login")
    public ResponseEntity<Cliente> fazLogin(@PathVariable UUID id, @RequestBody Map<String, String> bodyRequest) {
        Optional<Cliente> clienteProcurado = clienteRepository.findById(id);
        if (clienteProcurado.isPresent()) {
            if (clienteProcurado.get().getCpf().equals(bodyRequest.get("cpf")) && clienteProcurado.get().getSenha().equals(bodyRequest.get("senha"))) {
                Cliente clienteEncontrado = clienteProcurado.get();
                clienteEncontrado.realizarLogin();
                return ResponseEntity.ok(clienteRepository.save(clienteEncontrado));
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}/logoff")
    public ResponseEntity<Cliente> fazLogoff(@PathVariable UUID id) {
        Optional<Cliente> clienteProcurado = clienteRepository.findById(id);
        if (clienteProcurado.isPresent()) {
            Cliente clienteEncontrado = clienteProcurado.get();
            if (clienteEncontrado.isLogado()){
                clienteEncontrado.realizarLogoff();
                return ResponseEntity.ok(clienteRepository.save(clienteEncontrado));
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}/saldo")
    public ResponseEntity<String> pegaSaldo(@PathVariable UUID id) {
        Optional<Cliente> clienteProcurado = clienteRepository.findById(id);

        if(clienteProcurado.isPresent()) {
            Cliente clienteEncontrado = clienteProcurado.get();
            if (clienteEncontrado.isLogado()){
                String saldo = clienteEncontrado.verSaldo();
                clienteRepository.save(clienteEncontrado);
                return new ResponseEntity<>(saldo, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}/extrato")
    public ResponseEntity<String> pegaExtrato(@PathVariable UUID id) {
        Optional<Cliente> clienteProcurado = clienteRepository.findById(id);

        if(clienteProcurado.isPresent()) {
            Cliente clienteEncontrado = clienteProcurado.get();
            if (clienteEncontrado.isLogado()){
                String extrato = clienteEncontrado.verExtrato();
                clienteRepository.save(clienteEncontrado);
                return new ResponseEntity<>(extrato, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Cliente> cadastraCliente(@RequestBody Cliente cliente) {
        cliente.escolheConta(cliente.getRendaMensal());
        return new ResponseEntity<>(clienteRepository.save(cliente), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}/deletar")
    public ResponseEntity<Cliente> descadastraCliente(@PathVariable UUID id) {
        Optional<Cliente> clienteProcurado = clienteRepository.findById(id);

        if(clienteProcurado.isPresent()) {
            Cliente clienteEncontrado = clienteProcurado.get();
            if (clienteEncontrado.isLogado()){
                clienteRepository.deleteById(clienteEncontrado.getId());
                return ResponseEntity.ok(clienteEncontrado);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{id}/pix/cadastrar")
    public ResponseEntity<Cliente> cadastraChavePix(@PathVariable UUID id, @RequestBody Map<String, String> bodyRequest) {
        Optional<Cliente> clienteProcurado = clienteRepository.findById(id);

        if(clienteProcurado.isPresent()) {
            Cliente clienteEncontrado = clienteProcurado.get();
            if (clienteEncontrado.isLogado()){
                String chavePix = bodyRequest.get("pix");
                clienteEncontrado.cadastrarPix(chavePix);
                return ResponseEntity.ok(clienteRepository.save(clienteEncontrado));
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}/pix/deletar")
    public ResponseEntity<Cliente> descadastraChavePix(@PathVariable UUID id) {
        Optional<Cliente> clienteProcurado = clienteRepository.findById(id);

        if(clienteProcurado.isPresent()) {
            Cliente clienteEncontrado = clienteProcurado.get();
            if (clienteEncontrado.isLogado()){
                clienteEncontrado.descadastrarPix();
                return ResponseEntity.ok(clienteRepository.save(clienteEncontrado));
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{idOrigem}/transferencia/{idDestino}")
    public ResponseEntity<Cliente> fazTransferencia(@PathVariable UUID idOrigem,
                                                    @PathVariable UUID idDestino,
                                                    @RequestBody Map<String, String> bodyRequest) {
        Optional<Cliente> clienteProcuradoOrigem = clienteRepository.findById(idOrigem);
        Optional<Cliente> clienteProcuradoDestino = clienteRepository.findById(idDestino);

        if(clienteProcuradoOrigem.isPresent() && clienteProcuradoDestino.isPresent()) {
            String transferencia = bodyRequest.get("saldo");

            Cliente clienteEncontradoOrigem = clienteProcuradoOrigem.get();
            Cliente clienteEncontradoDestino = clienteProcuradoDestino.get();

            if (clienteEncontradoOrigem.isLogado()){
                if (Double.parseDouble(clienteEncontradoOrigem.getLimite()) >= Double.parseDouble(transferencia)){
                    String saldoAtualizadoOrigem = String.valueOf(Double.parseDouble(clienteEncontradoOrigem.verSaldo()) - Double.parseDouble(transferencia));
                    clienteEncontradoOrigem.realizarSaque(saldoAtualizadoOrigem);
                    clienteEncontradoOrigem.atualizarLimite();

                    String saldoAtualizadoDestino = String.valueOf(Double.parseDouble(clienteEncontradoDestino.verSaldo()) + Double.parseDouble(transferencia));
                    clienteEncontradoDestino.realizarDeposito(saldoAtualizadoDestino);
                    clienteEncontradoDestino.atualizarLimite();
                    clienteRepository.save(clienteEncontradoDestino);

                    Transacao transacaoTransferencia = new Transacao(idOrigem, "transferencia", transferencia, idDestino);
                    transacaoRepository.save(transacaoTransferencia);
                    return ResponseEntity.ok(clienteEncontradoOrigem);
                }
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}/deposito")
    public ResponseEntity<Cliente> fazDeposito(@PathVariable UUID id, @RequestBody Map<String, String> bodyRequest) {
        Optional<Cliente> clienteProcurado = clienteRepository.findById(id);

        if(clienteProcurado.isPresent()) {
            String deposito = bodyRequest.get("saldo");
            Cliente clienteEncontrado = clienteProcurado.get();
            if (clienteEncontrado.isLogado()){
                String saldoAtualizado = String.valueOf(Double.parseDouble(clienteEncontrado.verSaldo()) + Double.parseDouble(deposito));
                clienteEncontrado.realizarDeposito(saldoAtualizado);
                clienteEncontrado.atualizarLimite();
                Transacao transacaoDeposito = new Transacao(id, "deposito", deposito);
                transacaoRepository.save(transacaoDeposito);
                return ResponseEntity.ok(clienteRepository.save(clienteEncontrado));
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}/saque")
    public ResponseEntity<Cliente> fazSaque(@PathVariable UUID id, @RequestBody Map<String, String> bodyRequest) {
        Optional<Cliente> clienteProcurado = clienteRepository.findById(id);

        if(clienteProcurado.isPresent()) {
            String saque = bodyRequest.get("saldo");
            Cliente clienteEncontrado = clienteProcurado.get();
            if (clienteEncontrado.isLogado()) {
                if (Double.parseDouble(clienteEncontrado.getLimite()) >= Double.parseDouble(saque)) {
                    String saldoAtualizado = String.valueOf(Double.parseDouble(clienteEncontrado.verSaldo()) - Double.parseDouble(saque));
                    clienteEncontrado.realizarSaque(saldoAtualizado);
                    clienteEncontrado.atualizarLimite();
                    Transacao transacaoSaque = new Transacao(id, "saque", saque);
                    transacaoRepository.save(transacaoSaque);
                    return ResponseEntity.ok(clienteRepository.save(clienteEncontrado));
                }
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}/pagamento")
    public ResponseEntity<Cliente> fazPagamento(@PathVariable UUID id, @RequestBody Map<String, String> bodyRequest) {
        Optional<Cliente> clienteProcurado = clienteRepository.findById(id);

        if(clienteProcurado.isPresent()) {
            String pagamento = bodyRequest.get("saldo");
            Cliente clienteEncontrado = clienteProcurado.get();
            if (clienteEncontrado.isLogado()) {
                if (Double.parseDouble(clienteEncontrado.getLimite()) >= Double.parseDouble(pagamento)) {
                String saldoAtualizado = String.valueOf(Double.parseDouble(clienteEncontrado.verSaldo()) - Double.parseDouble(pagamento));
                clienteEncontrado.pagarConta(saldoAtualizado);
                clienteEncontrado.atualizarLimite();
                Transacao transacaoPagamento = new Transacao(id, "pagamento", pagamento);
                transacaoRepository.save(transacaoPagamento);
                return ResponseEntity.ok(clienteRepository.save(clienteEncontrado));
                }
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}/recuperar-senha")
    public ResponseEntity<Cliente> recuperaSenha(@PathVariable UUID id, @RequestBody Map<String, String> bodyRequest) {
        Optional<Cliente> clienteProcurado = clienteRepository.findById(id);

        if(clienteProcurado.isPresent()) {
            String senhaAtualizada = bodyRequest.get("senha");
            Cliente clienteEncontrado = clienteProcurado.get();
            if (clienteEncontrado.isLogado()){
                clienteEncontrado.setSenha(senhaAtualizada);
                return ResponseEntity.ok(clienteRepository.save(clienteEncontrado));
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("{id}/atualizar")
    public ResponseEntity<Cliente> atualizaCadastro(@PathVariable UUID id, @RequestBody Cliente bodyRequest) {
        Optional<Cliente> clienteProcurado = clienteRepository.findById(id);

        if(clienteProcurado.isPresent()) {
            Cliente clienteEncontrado = clienteProcurado.get();
            if (clienteEncontrado.isLogado()){
                clienteEncontrado.setTelefone(bodyRequest.getTelefone());
                clienteEncontrado.setEndereco(bodyRequest.getEndereco());
                clienteEncontrado.setRendaMensal(bodyRequest.getRendaMensal());
                clienteEncontrado.setEmail(bodyRequest.getEmail());
                clienteEncontrado.setSenha(bodyRequest.getSenha());
                return ResponseEntity.ok(clienteRepository.save(clienteEncontrado));
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
     */

}
