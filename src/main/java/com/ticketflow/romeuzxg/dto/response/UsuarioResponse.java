package com.ticketflow.romeuzxg.dto.response;

public record UsuarioResponse(
        Long id,
        String nome,
        String cpf,
        String email
) {
}
