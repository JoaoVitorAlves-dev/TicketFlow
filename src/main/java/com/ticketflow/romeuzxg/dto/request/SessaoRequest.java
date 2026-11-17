package com.ticketflow.romeuzxg.dto.request;

import com.ticketflow.romeuzxg.domain.Roles.StatusSessao;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record SessaoRequest(
        @NotNull Long eventoId,
        @NotNull Long localId,
        @NotNull LocalDateTime dataHora,
        @NotNull StatusSessao status
) {
}
