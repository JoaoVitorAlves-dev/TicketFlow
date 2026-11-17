package com.ticketflow.romeuzxg.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrganizadorRequest(
        @NotNull Long usuarioId,
        @NotBlank String nomeFantasia,
        @NotBlank String cnpj,
        @NotNull Boolean ativo
) {
}
