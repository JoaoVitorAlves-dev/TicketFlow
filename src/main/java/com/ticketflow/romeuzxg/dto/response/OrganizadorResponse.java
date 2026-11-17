package com.ticketflow.romeuzxg.dto.response;

public record OrganizadorResponse(
        Long id,
        String nomeUsuario,
        String nomeFantasia,
        String cnpj,
        Boolean ativa
) {
}
