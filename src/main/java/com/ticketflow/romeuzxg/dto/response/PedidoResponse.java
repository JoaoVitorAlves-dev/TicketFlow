package com.ticketflow.romeuzxg.dto.response;

import com.ticketflow.romeuzxg.domain.Roles.CategoriaEvento;
import com.ticketflow.romeuzxg.domain.Roles.ClassificacaoIndicativa;
import com.ticketflow.romeuzxg.domain.Roles.StatusEvento;

public record PedidoResponse(
        Long id,
        Long usuarioId,
        String titulo,
        CategoriaEvento categoria,
        StatusEvento status,
        ClassificacaoIndicativa classificacao
) {
}
