package com.ticketflow.romeuzxg.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CheckinRequest(
        @NotNull Long ingressoId,
        @NotNull LocalDateTime feitoEm,
        @NotBlank String portaria
) {
}
