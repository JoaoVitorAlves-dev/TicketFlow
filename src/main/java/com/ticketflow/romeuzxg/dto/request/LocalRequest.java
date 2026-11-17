package com.ticketflow.romeuzxg.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LocalRequest(
        @NotBlank String nome,
        @NotBlank String cidade,
        @NotBlank String estado,
        @NotNull Integer capacidade
) {
}
