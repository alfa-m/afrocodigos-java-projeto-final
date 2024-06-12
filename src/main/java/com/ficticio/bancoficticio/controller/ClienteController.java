package com.ficticio.bancoficticio.controller;

import com.ficticio.bancoficticio.model.entity.Cliente;
import com.ficticio.bancoficticio.repository.ClienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/bancoficticio")
public class ClienteController {
    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @GetMapping("/{id}/saldo")
    public ResponseEntity<String> pegaSaldo(@PathVariable UUID id) {
        Optional<Cliente> clienteProcurado = clienteRepository.findById(id);

        if(clienteProcurado.isPresent()) {
            Cliente clienteEncontrado = clienteProcurado.get();
            String saldo = clienteEncontrado.getRendaMensal();
            return ResponseEntity.ok("Saldo do cliente " + clienteEncontrado.getNome() + ": " + saldo);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
