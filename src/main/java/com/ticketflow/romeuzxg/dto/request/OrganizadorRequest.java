package com.ticketflow.romeuzxg.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record OrganizadorRequest(
        @NotNull UUID usuarioId,
        @NotBlank String nomeFantasia,
        @NotBlank String cnpj,
        @NotNull Boolean ativo
) {
}
