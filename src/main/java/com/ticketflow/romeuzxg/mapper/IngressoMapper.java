package com.ticketflow.romeuzxg.mapper;

import com.ticketflow.romeuzxg.domain.Assento;
import com.ticketflow.romeuzxg.domain.Ingresso;
import com.ticketflow.romeuzxg.domain.Pedido;
import com.ticketflow.romeuzxg.domain.Sessao;
import com.ticketflow.romeuzxg.dto.request.IngressoRequest;
import com.ticketflow.romeuzxg.dto.response.IngressoResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class IngressoMapper {

    public Ingresso toEntity(IngressoRequest req, Assento assento, Pedido pedido, Sessao sessao) {
        Ingresso ingresso = new Ingresso();
        ingresso.setAssento(assento);
        ingresso.setCodigoQr(req.codigoQr());
        ingresso.setPedido(pedido);
        ingresso.setSessao(sessao);
        ingresso.setStatus(req.status());
        return ingresso;
    }

    public IngressoResponse toDTO(Ingresso ing) {
        return new IngressoResponse(
                ing.getId(), ing.getPedido(), ing.getSessao(), ing.getAssento(), ing.getCodigoQr(), ing.getStatus()
        );
    }

}
