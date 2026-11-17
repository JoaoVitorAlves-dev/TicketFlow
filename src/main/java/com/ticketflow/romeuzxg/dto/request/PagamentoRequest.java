package com.ticketflow.romeuzxg.dto.request;

import com.ticketflow.romeuzxg.domain.Roles.MetodoPagamento;
import com.ticketflow.romeuzxg.domain.Roles.StatusPagamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PagamentoRequest(
        @NotNull Long pedidoId,
        @NotNull MetodoPagamento metodo,
        @NotNull StatusPagamento status,
        @NotBlank String idempotency_key
) {
}
