package com.ticketflow.romeuzxg.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PedidoRequest(
        @NotNull UUID usuarioId,
        @NotNull @PositiveOrZero BigDecimal total,
        @NotBlank String cupomCodigo,
        @NotNull LocalDateTime expiraEm,
        @NotNull Long version
) {
}
