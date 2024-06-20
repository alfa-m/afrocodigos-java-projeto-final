package com.ficticio.bancoficticio.controller;

import com.ficticio.bancoficticio.exception.ClienteException;
import com.ficticio.bancoficticio.exception.ContaException;
import com.ficticio.bancoficticio.model.entity.Conta;
import com.ficticio.bancoficticio.model.entity.Transacao;
import com.ficticio.bancoficticio.repository.ContaRepository;
import com.ficticio.bancoficticio.service.ContaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/conta")
public class ContaController {
    private final ContaRepository contaRepository;
    private final ContaService contaService;

    public ContaController(ContaRepository contaRepository, ContaService contaService) {
        this.contaRepository = contaRepository;
        this.contaService = contaService;
    }

    @GetMapping("/contas")
    public List<Conta> listaContas() {
        return contaRepository.findAll();
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<Object> veConta(@PathVariable UUID idCliente){
        try {
            Conta conta = contaService.verConta(idCliente);
            return ResponseEntity.status(HttpStatus.OK).body(conta);
        } catch (ClienteException.ClienteNaoLogadoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (ContaException.ContaNaoExisteException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{idConta}/saldo")
    public ResponseEntity<Object> veSaldo(@PathVariable UUID idConta){
        try {
            double saldo = contaService.verSaldo(idConta);
            return ResponseEntity.ok("Saldo atual da conta com id " + idConta +  ": R$ " + saldo);
        } catch (ClienteException.ClienteNaoLogadoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (ContaException.ContaNaoExisteException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{idConta}/extrato")
    public ResponseEntity<Object> veExtrato(@PathVariable UUID idConta,
                                            @RequestParam (name = "mes", required = true) int mes,
                                            @RequestParam (name = "ano", required = true) int ano){
        LocalDate diaInicio = LocalDate.of(ano, mes, 1);
        LocalDateTime dataInicio = diaInicio.atStartOfDay();

        int ultimoDia = YearMonth.of(ano, mes).atEndOfMonth().getDayOfMonth();
        LocalDate diaFim = LocalDate.of(ano, mes, ultimoDia);
        LocalDateTime dataFim = diaFim.atTime(23,59,59);

        try {
            List<Transacao> transacoes = contaService.verExtrato(idConta, dataInicio, dataFim);
            return ResponseEntity.ok(transacoes);
        } catch (ClienteException.ClienteNaoLogadoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (ContaException.ContaNaoExisteException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{idConta}/deposito")
    public ResponseEntity<Object> fazDeposito(@PathVariable UUID idConta,
                                              @RequestBody Map<String, String> depositoBody){
        try {
            contaService.fazerDeposito(idConta, Double.parseDouble(depositoBody.get("quantia")));
            return ResponseEntity.status(HttpStatus.OK).body(contaRepository.findById(idConta));
        } catch (ClienteException.ClienteNaoLogadoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (ContaException.ContaNaoExisteException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{idConta}/saque")
    public ResponseEntity<Object> fazSaque(@PathVariable UUID idConta,
                                              @RequestBody Map<String, String> saqueBody){
        try {
            contaService.fazerSaque(idConta, Double.parseDouble(saqueBody.get("quantia")));
            return ResponseEntity.status(HttpStatus.OK).body(contaRepository.findById(idConta));
        } catch (ClienteException.ClienteNaoLogadoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (ContaException.ContaNaoExisteException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ContaException.SemLimiteException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PatchMapping("/{idConta}/pagamento-de-conta")
    public ResponseEntity<Object> pagaConta(@PathVariable UUID idConta,
                                            @RequestBody Map<String, String> pagamentoBody){
        try {
            contaService.fazerPagamento(idConta, Double.parseDouble(pagamentoBody.get("quantia")));
            return ResponseEntity.status(HttpStatus.OK).body(contaRepository.findById(idConta));
        } catch (ClienteException.ClienteNaoLogadoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (ContaException.ContaNaoExisteException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{idContaOrigem}/transferencia")
    public ResponseEntity<Object> fazTransferencia(@PathVariable UUID idContaOrigem,
                                                   @RequestBody Map<String, String> transferenciaBody){
        try {
            contaService.fazerTransferencia(idContaOrigem, UUID.fromString(transferenciaBody.get("idContaDestino")), Double.parseDouble(transferenciaBody.get("quantia")));
            return ResponseEntity.status(HttpStatus.OK).body(contaRepository.findById(idContaOrigem));
        } catch (ClienteException.ClienteNaoLogadoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (ContaException.ContaNaoExisteException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ContaException.SemLimiteException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (ContaException.ContaSemPermissaoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PatchMapping("/{idConta}/pix")
    public ResponseEntity<Object> fazPiz(@PathVariable UUID idConta,
                                         @RequestBody Map<String, String> pixBody){
        try {
            contaService.fazerPix(idConta, pixBody.get("chavePix"), Double.parseDouble(pixBody.get("quantia")));
            return ResponseEntity.status(HttpStatus.OK).body(contaRepository.findById(idConta));
        } catch (ClienteException.ClienteNaoLogadoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (ContaException.ContaNaoExisteException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ContaException.SemLimiteException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (ContaException.SemChavePixException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/{idConta}/pix/cadastro")
    public ResponseEntity<Object> cadastraChavePix(@PathVariable UUID idConta,
                                                   @RequestBody Map<String, String> chavePixBody){
        try {
            contaService.cadastraChavePix(idConta, chavePixBody.get("chavePix"));
            return ResponseEntity.status(HttpStatus.CREATED).body(contaRepository.findById(idConta));
        } catch (ClienteException.ClienteNaoLogadoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (ContaException.ContaNaoExisteException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{idConta}/pix/descadastro")
    public ResponseEntity<Object> descadastraChavePix(@PathVariable UUID idConta){
        try {
            contaService.descadastraChavePix(idConta);
            return ResponseEntity.status(HttpStatus.CREATED).body(contaRepository.findById(idConta));
        } catch (ClienteException.ClienteNaoLogadoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (ContaException.ContaNaoExisteException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
