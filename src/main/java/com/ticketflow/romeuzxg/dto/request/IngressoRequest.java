package com.ticketflow.romeuzxg.dto.request;

import com.ticketflow.romeuzxg.domain.Roles.StatusIngresso;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record IngressoRequest(
        @NotNull Long pedidoId,
        @NotNull Long sessaoId,
        @NotNull Long assentoId,
        @NotNull UUID codigoQr,
        @NotNull StatusIngresso status
) {
}
