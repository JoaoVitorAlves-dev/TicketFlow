package com.ticketflow.romeuzxg.dto.response;

import java.util.UUID;

public record UsuarioResponse(
        UUID id,
        String nome,
        String cpf,
        String email
) {
}
