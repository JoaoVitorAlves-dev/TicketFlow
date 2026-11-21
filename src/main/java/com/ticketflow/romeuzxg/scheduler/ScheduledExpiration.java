package com.ticketflow.romeuzxg.scheduler;

import com.ticketflow.romeuzxg.service.PagamentoService;
import com.ticketflow.romeuzxg.service.PedidoService;
import com.ticketflow.romeuzxg.service.SessaoService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledExpiration {

    private final PedidoService pedidoService;
    private final SessaoService sessaoService;
    private final PagamentoService pagamentoService;

    public ScheduledExpiration(PedidoService pedidoService, SessaoService sessaoService, PagamentoService pagamentoService) {
        this.pedidoService = pedidoService;
        this.sessaoService = sessaoService;
        this.pagamentoService = pagamentoService;
    }

    @Scheduled(fixedRate = 200000)
    public void executarPedidos() {
        pedidoService.scheduled();
    }

    @Scheduled(fixedRate = 120000)
    public void executarSessao() {
        sessaoService.deletar();
    }

    @Scheduled(fixedRate = 390000)
    public void executarPagamento() {
        pagamentoService.scheduler();
    }

}
