package com.ticketflow.romeuzxg.dto.request;

import jakarta.validation.constraints.NotNull;

public record IngressoRequest(
        @NotNull Long pedidoId,
        @NotNull Long sessaoId,
        @NotNull Long assentoId
) {
}
