package com.ticketflow.romeuzxg.dto.response;

public record LocalResponse(
        Long id,
        String nome,
        String cidade,
        String estado,
        Integer capacidade
) {
}
