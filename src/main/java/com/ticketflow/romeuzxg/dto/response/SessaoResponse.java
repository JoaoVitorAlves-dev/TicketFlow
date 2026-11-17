package com.ticketflow.romeuzxg.dto.response;

import com.ticketflow.romeuzxg.domain.Local;
import com.ticketflow.romeuzxg.domain.Roles.StatusSessao;

import java.time.LocalDateTime;

public record SessaoResponse(
        Long id,
        Local local,
        LocalDateTime dataHora,
        StatusSessao status
) {
}
