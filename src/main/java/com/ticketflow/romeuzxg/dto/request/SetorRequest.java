package com.ticketflow.romeuzxg.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record SetorRequest(
        @NotNull Long localId,
        @NotBlank String nome,
        @NotNull @PositiveOrZero BigDecimal precoBase
) {
}
