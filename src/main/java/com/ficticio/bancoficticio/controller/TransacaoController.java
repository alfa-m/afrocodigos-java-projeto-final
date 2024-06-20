package com.ficticio.bancoficticio.controller;

import com.ficticio.bancoficticio.model.entity.Transacao;
import com.ficticio.bancoficticio.repository.TransacaoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {
    private final TransacaoRepository transacaoRepository;

    public TransacaoController(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    @GetMapping("/transacoes")
    public List<Transacao> listaTransacoes(){
        return transacaoRepository.findAll();
    }

    @GetMapping("/depositos")
    public List<Transacao> listaDepositos(){
        return transacaoRepository.findTransacaosByTipoContainingIgnoreCase("depósito");
    }

    @GetMapping("/saques")
    public List<Transacao> listaSaques(){
        return transacaoRepository.findTransacaosByTipoContainingIgnoreCase("saque");
    }

    @GetMapping("/transferencias")
    public List<Transacao> listaTransferencias(){
        return transacaoRepository.findTransacaosByTipoContainingIgnoreCase("transferência");
    }

    @GetMapping("/pagamentos")
    public List<Transacao> listaPagamentos(){
        return transacaoRepository.findTransacaosByTipoContainingIgnoreCase("pagamento");
    }

    @GetMapping("/pix")
    public List<Transacao> listaPix(){
        return transacaoRepository.findTransacaosByTipoContainingIgnoreCase("pix");
    }

}
