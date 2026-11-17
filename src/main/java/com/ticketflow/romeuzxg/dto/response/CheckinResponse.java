package com.ticketflow.romeuzxg.dto.response;

import com.ticketflow.romeuzxg.domain.Ingresso;

import java.time.LocalDateTime;

public record CheckinResponse(
        Long id,
        Ingresso ingresso,
        LocalDateTime feitoEm,
        String portaria
) {
}
