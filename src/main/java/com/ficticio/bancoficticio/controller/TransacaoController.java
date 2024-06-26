package com.ficticio.bancoficticio.controller;

import com.ficticio.bancoficticio.model.entity.Transacao;
import com.ficticio.bancoficticio.repository.TransacaoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {
    private final TransacaoRepository transacaoRepository;

    public TransacaoController(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    @GetMapping("/transacoes")
    public List<Transacao> listaTransacoes(@RequestParam(name = "id", required = false) UUID id){
        if(id != null){
            return transacaoRepository.findTransacaosByIdContaIs(id);
        }

        return transacaoRepository.findAll();
    }

    @GetMapping("/depositos")
    public List<Transacao> listaDepositos(@RequestParam(name = "id", required = false) UUID id){
        if(id != null){
            return transacaoRepository.findTransacaosByTipoContainingIgnoreCaseAndIdContaIs("depósito", id);
        }

        return transacaoRepository.findTransacaosByTipoContainingIgnoreCase("depósito");
    }

    @GetMapping("/saques")
    public List<Transacao> listaSaques(@RequestParam(name = "id", required = false) UUID id){
        if(id != null){
            return transacaoRepository.findTransacaosByTipoContainingIgnoreCaseAndIdContaIs("saque", id);
        }

        return transacaoRepository.findTransacaosByTipoContainingIgnoreCase("saque");
    }

    @GetMapping("/transferencias")
    public List<Transacao> listaTransferencias(@RequestParam(name = "id", required = false) UUID id){
        if(id != null){
            return transacaoRepository.findTransacaosByTipoContainingIgnoreCaseAndIdContaIs("transferência", id);
        }

        return transacaoRepository.findTransacaosByTipoContainingIgnoreCase("transferência");
    }

    @GetMapping("/pagamentos")
    public List<Transacao> listaPagamentos(@RequestParam(name = "id", required = false) UUID id){
        if(id != null){
            return transacaoRepository.findTransacaosByTipoContainingIgnoreCaseAndIdContaIs("pagamento", id);
        }

        return transacaoRepository.findTransacaosByTipoContainingIgnoreCase("pagamento");
    }

    @GetMapping("/pix")
    public List<Transacao> listaPix(@RequestParam(name = "id", required = false) UUID id){
        if(id != null){
            return transacaoRepository.findTransacaosByTipoContainingIgnoreCaseAndIdContaIs("pix", id);
        }

        return transacaoRepository.findTransacaosByTipoContainingIgnoreCase("pix");
    }

}
