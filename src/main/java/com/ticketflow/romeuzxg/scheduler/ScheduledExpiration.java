package com.ticketflow.romeuzxg.scheduler;

import com.ticketflow.romeuzxg.service.PedidoService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledExpiration {

    private final PedidoService pedidoService;

    public ScheduledExpiration(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @Scheduled(fixedRate = 500000)
    public void executarPedidos() {
        pedidoService.scheduled();
    }


}
