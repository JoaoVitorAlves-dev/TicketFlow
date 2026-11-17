package com.ticketflow.romeuzxg.dto.response;

import com.ticketflow.romeuzxg.domain.Setor;

public record AssentoResponse(
        Long id,
        Setor setor,
        String fila,
        Integer numero,
        Boolean ativo
) {
}
