package com.ticketflow.romeuzxg.scheduler;

import com.ticketflow.romeuzxg.service.PedidoService;
import com.ticketflow.romeuzxg.service.SessaoService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledExpiration {

    private final PedidoService pedidoService;
    private final SessaoService sessaoService;

    public ScheduledExpiration(PedidoService pedidoService, SessaoService sessaoService) {
        this.pedidoService = pedidoService;
        this.sessaoService = sessaoService;
    }

    @Scheduled(fixedRate = 500000)
    public void executarPedidos() {
        pedidoService.scheduled();
    }

    @Scheduled(fixedRate = 500000)
    public void executarSessao() {
        sessaoService.deletar();
    }


}
