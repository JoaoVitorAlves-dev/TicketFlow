package com.ticketflow.romeuzxg.mapper;

import com.ticketflow.romeuzxg.domain.Evento;
import com.ticketflow.romeuzxg.domain.Organizador;
import com.ticketflow.romeuzxg.dto.request.EventoRequest;
import com.ticketflow.romeuzxg.dto.response.EventoResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EventoMapper {

    public Evento toEntity(EventoRequest req, Organizador organizador) {
        Evento evento = new Evento();
        evento.setAtivo(req.ativo());
        evento.setOrganizador(organizador);
        evento.setCategoriaEvento(req.categoria());
        evento.setClassificacao(req.classificacao());
        evento.setStatusEvento(req.status());
        evento.setTitulo(req.titulo());
        return evento;
    }

    public EventoResponse toDTO(Evento req) {
        return new EventoResponse(
                req.getId(), req.getOrganizador(), req.getTitulo(), req.getCategoriaEvento(), req.getStatusEvento(), req.getClassificacao()
        );
    }

}
