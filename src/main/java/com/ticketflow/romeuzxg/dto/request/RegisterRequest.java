package com.ticketflow.romeuzxg.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank String nome,
        @NotBlank String cpf,
        @NotBlank String email,
        @NotBlank String password
) {
}
