package com.ticketflow.romeuzxg.dto.response;

import com.ticketflow.romeuzxg.domain.Local;

import java.math.BigDecimal;

public record SetorResponse(
        Long id,
        Local local,
        String nome,
        BigDecimal precoBase
) {
}
