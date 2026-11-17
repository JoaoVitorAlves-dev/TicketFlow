package com.ticketflow.romeuzxg.dto.response;

import com.ticketflow.romeuzxg.domain.Organizador;
import com.ticketflow.romeuzxg.domain.Roles.CategoriaEvento;
import com.ticketflow.romeuzxg.domain.Roles.ClassificacaoIndicativa;
import com.ticketflow.romeuzxg.domain.Roles.StatusEvento;

public record EventoResponse(
        Long id,
        Organizador organizador,
        String titulo,
        CategoriaEvento categoria,
        StatusEvento status,
        ClassificacaoIndicativa classificacao
) {
}
