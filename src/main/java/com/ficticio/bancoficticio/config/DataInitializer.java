package com.ficticio.bancoficticio.config;

import com.ficticio.bancoficticio.repository.ClienteRepository;
import com.ficticio.bancoficticio.model.entity.Cliente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataInitializer implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);
    private final ClienteRepository clienteRepository;

    public DataInitializer(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    /*
    public static final List<Cliente> listaClientes = List.of(
            new Cliente("123.456.789-00", "José Maria", "01/01/2000", "1234-5678", "Endereço do José, 001", "3600.00", "josemaria@email.com", "senha123"),
            new Cliente("123.456.789-01", "Maria Santos", "02/01/2000", "2345-6789", "Endereço da Maria, 001", "5400.00", "mariasantos@email.com", "senha234"),
            new Cliente("123.456.789-02", "João Pereira", "03/01/2000", "3456-7890", "Endereço do João, 001", "3600.00", "joaopereira@email.com", "senha345")
    );
    */

    @Override
    public void run(String... args) throws Exception {
        log.info("Banco de dados inicializado com sucesso!");
        //clienteRepository.saveAll(listaClientes);
    }
}
