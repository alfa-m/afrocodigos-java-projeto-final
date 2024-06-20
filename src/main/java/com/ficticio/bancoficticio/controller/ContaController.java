package com.ficticio.bancoficticio.controller;

import com.ficticio.bancoficticio.exception.ClienteException;
import com.ficticio.bancoficticio.exception.ContaException;
import com.ficticio.bancoficticio.model.entity.Conta;
import com.ficticio.bancoficticio.model.entity.Transacao;
import com.ficticio.bancoficticio.repository.ClienteRepository;
import com.ficticio.bancoficticio.repository.ContaRepository;
import com.ficticio.bancoficticio.repository.TransacaoRepository;
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
    private final ClienteRepository clienteRepository;
    private final ContaService contaService;
    private final TransacaoRepository transacaoRepository;

    public ContaController(ContaRepository contaRepository, ClienteRepository clienteRepository, ContaService contaService, TransacaoRepository transacaoRepository) {
        this.contaRepository = contaRepository;
        this.clienteRepository = clienteRepository;
        this.contaService = contaService;
        this.transacaoRepository = transacaoRepository;
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

    @PatchMapping("/{id}/deposito")
    public ResponseEntity<Object> fazDeposito(@PathVariable UUID id,
                                              @RequestBody Map<String, String> depositoBody){
        try {
            contaService.fazerDeposito(id, Double.parseDouble(depositoBody.get("quantia")));
            return ResponseEntity.status(HttpStatus.OK).body(contaRepository.findById(id));
        } catch (ClienteException.ClienteNaoLogadoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (ContaException.ContaNaoExisteException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}/saque")
    public ResponseEntity<Object> fazSaque(@PathVariable UUID id,
                                              @RequestBody Map<String, String> saqueBody){
        try {
            contaService.fazerSaque(id, Double.parseDouble(saqueBody.get("quantia")));
            return ResponseEntity.status(HttpStatus.OK).body(contaRepository.findById(id));
        } catch (ClienteException.ClienteNaoLogadoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (ContaException.ContaNaoExisteException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ContaException.SemLimiteException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PatchMapping("/{id}/pagamento-de-conta")
    public ResponseEntity<Object> pagaConta(@PathVariable UUID id,
                                            @RequestBody Map<String, String> pagamentoBody){
        try {
            contaService.fazerPagamento(id, Double.parseDouble(pagamentoBody.get("quantia")));
            return ResponseEntity.status(HttpStatus.OK).body(contaRepository.findById(id));
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

    @PatchMapping("/{id}/pix")
    public ResponseEntity<Object> fazPiz(@PathVariable UUID id,
                                         @RequestBody Map<String, String> pixBody){
        try {
            contaService.fazerPix(id, pixBody.get("chavePix"), Double.parseDouble(pixBody.get("quantia")));
            return ResponseEntity.status(HttpStatus.OK).body(contaRepository.findById(id));
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

    @PostMapping("/{id}/pix/cadastro")
    public ResponseEntity<Object> cadastraChavePix(@PathVariable UUID id,
                                 @RequestBody Map<String, String> chavePixBody){
        try {
            contaService.cadastraChavePix(id, chavePixBody.get("chavePix"));
            return ResponseEntity.status(HttpStatus.CREATED).body(contaRepository.findById(id));
        } catch (ClienteException.ClienteNaoLogadoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (ContaException.ContaNaoExisteException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}/pix/descadastro")
    public ResponseEntity<Object> descadastraChavePix(@PathVariable UUID id){
        try {
            contaService.descadastraChavePix(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(contaRepository.findById(id));
        } catch (ClienteException.ClienteNaoLogadoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (ContaException.ContaNaoExisteException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
