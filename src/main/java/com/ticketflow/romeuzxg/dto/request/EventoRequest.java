package com.ticketflow.romeuzxg.dto.request;

import com.ticketflow.romeuzxg.domain.Roles.CategoriaEvento;
import com.ticketflow.romeuzxg.domain.Roles.ClassificacaoIndicativa;
import com.ticketflow.romeuzxg.domain.Roles.StatusEvento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EventoRequest(
        @NotNull Long organizadorId,
        @NotBlank String titulo,
        @NotNull CategoriaEvento categoria,
        @NotNull StatusEvento status,
        @NotNull ClassificacaoIndicativa classificacao,
        @NotNull Boolean ativo
) {
}
