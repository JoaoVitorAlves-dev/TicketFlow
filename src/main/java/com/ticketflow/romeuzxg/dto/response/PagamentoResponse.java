package com.ticketflow.romeuzxg.dto.response;

import com.ticketflow.romeuzxg.domain.Pedido;
import com.ticketflow.romeuzxg.domain.Roles.MetodoPagamento;
import com.ticketflow.romeuzxg.domain.Roles.StatusPagamento;

public record PagamentoResponse(
        Long id,
        Pedido pedido,
        MetodoPagamento metodo,
        StatusPagamento status,
        String idempotencyKey
) {
}
