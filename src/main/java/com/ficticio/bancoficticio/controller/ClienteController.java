package com.ficticio.bancoficticio.controller;

import com.ficticio.bancoficticio.exception.ClienteException;
import com.ficticio.bancoficticio.model.entity.Cliente;
import com.ficticio.bancoficticio.repository.ClienteRepository;
import com.ficticio.bancoficticio.repository.ContaRepository;
import com.ficticio.bancoficticio.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    private final ClienteService clienteService;
    private final ContaRepository contaRepository;
    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteService clienteService, ContaRepository contaRepository, ClienteRepository clienteRepository) {
        this.clienteService = clienteService;
        this.contaRepository = contaRepository;
        this.clienteRepository = clienteRepository;
    }

    @GetMapping("/clientes")
    public List<Cliente> listaClientes(){
        return clienteRepository.findAll();
    }

    @PostMapping("/cadastro")
    public ResponseEntity<Object> criaConta(@RequestBody Cliente clienteBody) {
        try {
            Cliente cliente = clienteService.cadastrarCliente(clienteBody);
            contaRepository.save(clienteService.selecionarConta(cliente));

            return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
        } catch (ClienteException.ClienteJaCadastradoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{idCliente}/encerramento-de-conta")
    public ResponseEntity<Object> encerraConta(@PathVariable UUID idCliente) {
        try {
            Cliente cliente = clienteRepository.findById(idCliente).get();
            clienteService.descadastrarCliente(cliente);
            return ResponseEntity.noContent().build();
        } catch (ClienteException.ClienteNaoCadastradoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ClienteException.ClienteNaoLogadoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PatchMapping("/login")
    public ResponseEntity<Object> fazLogin(@RequestBody Map<String, String> loginBody) {
        try {
            Cliente cliente = clienteRepository.findClienteByCpfAndSenha(loginBody.get("cpf"), loginBody.get("senha"));

            if (cliente != null) {
                clienteService.logarCliente(cliente);
                clienteRepository.save(cliente);

                return ResponseEntity.status(HttpStatus.OK).body(cliente);
            } else {
                throw new ClienteException.LoginIncorretoException();
            }
        } catch (ClienteException.LoginIncorretoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/redefinir-senha")
    public ResponseEntity<Object> redefineSenha(@RequestBody Map<String, String> novaSenhaBody) {
        try {
            Cliente cliente = clienteRepository.findClienteByCpfAndEmail(novaSenhaBody.get("cpf"), novaSenhaBody.get("email"));
            clienteService.redefinirSenha(cliente, novaSenhaBody.get("senha"));
            return ResponseEntity.ok().build();
        } catch (ClienteException.ClienteNaoCadastradoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{idCliente}/logoff")
    public ResponseEntity<Object> fazLogoff(@PathVariable UUID idCliente) {
        try {
            Cliente cliente = clienteRepository.findById(idCliente)
                    .orElseThrow(() -> new ClienteException.ClienteNaoCadastradoException());

            if (cliente.isLogado()) {
                clienteService.deslogarCliente(cliente);
                return ResponseEntity.status(HttpStatus.OK).body(cliente);
            } else {
                throw new ClienteException.ClienteNaoLogadoException();
            }
        } catch (ClienteException.ClienteNaoCadastradoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ClienteException.ClienteNaoLogadoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PatchMapping("/{idCliente}/upgrade-de-conta")
    public ResponseEntity<Object> atualizaConta(@PathVariable UUID idCliente){
        try {
            clienteService.upgradeConta(idCliente);
            return ResponseEntity.ok().build();
        } catch (ClienteException.ClienteNaoCadastradoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ClienteException.ClienteNaoLogadoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (ClienteException.RendaBaixaException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/{idCliente}/atualizar-cadastro")
    public ResponseEntity<Object> atualizaCadastro(@PathVariable UUID idCliente, @RequestBody Cliente cliente) {
        try {
            clienteService.atualizarCliente(idCliente, cliente);
            return ResponseEntity.status(HttpStatus.OK).body(cliente);
        } catch(ClienteException.ClienteNaoCadastradoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch(ClienteException.ClienteNaoLogadoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
