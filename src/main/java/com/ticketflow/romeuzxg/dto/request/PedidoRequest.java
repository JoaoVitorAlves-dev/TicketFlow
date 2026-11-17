package com.ticketflow.romeuzxg.dto.request;

import com.ticketflow.romeuzxg.domain.Roles.StatusPedido;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PedidoRequest(
        @NotNull Long usuarioId,
        @NotNull StatusPedido status,
        @NotNull @PositiveOrZero BigDecimal total,
        @NotBlank String cupomCodigo,
        @NotNull LocalDateTime expiraEm,
        @NotNull Long version
) {
}
