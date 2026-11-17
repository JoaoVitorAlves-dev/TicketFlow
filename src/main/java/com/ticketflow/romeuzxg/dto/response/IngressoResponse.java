package com.ticketflow.romeuzxg.dto.response;

import com.ticketflow.romeuzxg.domain.Assento;
import com.ticketflow.romeuzxg.domain.Pedido;
import com.ticketflow.romeuzxg.domain.Roles.StatusIngresso;
import com.ticketflow.romeuzxg.domain.Sessao;

import java.util.UUID;

public record IngressoResponse(
        Long id,
        Pedido pedido,
        Sessao sessao,
        Assento assento,
        UUID codigoQr,
        StatusIngresso status
) {
}
