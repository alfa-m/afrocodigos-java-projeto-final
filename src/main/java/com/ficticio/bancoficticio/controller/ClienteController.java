package com.ficticio.bancoficticio.controller;

import com.ficticio.bancoficticio.exception.ClienteException;
import com.ficticio.bancoficticio.model.entity.Cliente;
import com.ficticio.bancoficticio.repository.ClienteRepository;
import com.ficticio.bancoficticio.repository.ContaRepository;
import com.ficticio.bancoficticio.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/olar")
    public String helloWorld(){
        return "OLAR";
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

    @DeleteMapping("encerramento-de-conta")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void encerraConta(@RequestBody Cliente clienteBody) {
        clienteService.descadastrarCliente(clienteBody);
    }

    @PutMapping("/login")
    public ResponseEntity<Object> fazLogin(@RequestBody Map<String, String> loginBody){
        try {
            Cliente cliente = clienteRepository.findClienteByCpfAndSenha(loginBody.get("cpf"), loginBody.get("senha"));

            if (cliente != null){
                clienteService.logarCliente(cliente);
                clienteRepository.save(cliente);

                return ResponseEntity.status(HttpStatus.OK).body(cliente);
            } else {
                throw new ClienteException.LoginIncorretoException();
            }
        } catch (ClienteException.LoginIncorretoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/logoff")
    public ResponseEntity<Object> fazLogoff(@RequestBody Map<String, String> logoffBody){
        try {
            Cliente cliente = clienteRepository.findById(UUID.fromString(logoffBody.get("id")))
                    .orElseThrow(() -> new ClienteException.ClienteNaoCadastradoException());
            clienteService.deslogarCliente(cliente);

            if (cliente.isLogado()){
                return ResponseEntity.status(HttpStatus.OK).body(cliente);
            } else {
                throw new ClienteException.ClienteNaoLogado();
            }
        } catch (ClienteException.ClienteNaoCadastradoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ClienteException.ClienteNaoLogado e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    //@PutMapping("/atualizar-cadastro")

    //@PatchMapping("/redefinir-senha")
}
