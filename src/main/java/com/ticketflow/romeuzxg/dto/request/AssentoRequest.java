package com.ticketflow.romeuzxg.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AssentoRequest(
        @NotNull Long setorId,
        @NotBlank String fila,
        @NotNull Integer numero,
        @NotNull Boolean ativo
) {
}
