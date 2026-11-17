package com.ticketflow.romeuzxg.dto.response;

import com.ticketflow.romeuzxg.domain.Roles.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PedidoResponse(
        Long id,
        UUID usuarioId,
        StatusPedido status,
        BigDecimal total,
        String cupomCodigo,
        LocalDateTime expiraEm,
        Long version
) {
}
