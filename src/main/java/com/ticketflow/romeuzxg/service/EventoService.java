package com.ticketflow.romeuzxg.service;

import com.ticketflow.romeuzxg.domain.Evento;
import com.ticketflow.romeuzxg.domain.Organizador;
import com.ticketflow.romeuzxg.domain.Roles.StatusEvento;
import com.ticketflow.romeuzxg.dto.request.EventoRequest;
import com.ticketflow.romeuzxg.dto.response.EventoResponse;
import com.ticketflow.romeuzxg.mapper.EventoMapper;
import com.ticketflow.romeuzxg.repository.EventoRepository;
import com.ticketflow.romeuzxg.repository.OrganizadorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;
    private final OrganizadorRepository organizadorRepository;

    public EventoService(EventoRepository eventoRepository, OrganizadorRepository organizadorRepository) {
        this.eventoRepository = eventoRepository;
        this.organizadorRepository = organizadorRepository;
    }

    public Page<EventoResponse> listar(Pageable pageable) {
        return eventoRepository.findAll(pageable).map(EventoMapper::toDTO);
    }

    public EventoResponse criar(EventoRequest req) {
        Organizador organizador = organizadorRepository.findById(req.organizadorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Evento entity = EventoMapper.toEntity(req, organizador);

        if (!organizador.getUsuario().getAtivo() || !organizador.getAtivo() || !entity.getAtivo() || !entity.getOrganizador().getAtivo()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        entity.setStatusEvento(StatusEvento.PUBLICADO);

        return EventoMapper.toDTO(eventoRepository.save(entity));
    }

    public EventoResponse atualizar(Long id, EventoRequest req) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Organizador organizador = organizadorRepository.findById(req.organizadorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!evento.getOrganizador().getUsuario().equals(organizador.getUsuario())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (!evento.getAtivo() || !evento.getOrganizador().getAtivo() || !evento.getOrganizador().getUsuario().getAtivo()
                || !organizador.getAtivo() || !organizador.getUsuario().getAtivo()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        evento.setStatusEvento(req.status());
        evento.setTitulo(req.titulo());
        evento.setClassificacao(req.classificacao());
        evento.setCategoriaEvento(req.categoria());

        return EventoMapper.toDTO(eventoRepository.save(evento));
    }

    public EventoResponse atualizarProprio(Long id, EventoRequest req, Authentication auth) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Organizador organizador = organizadorRepository.findById(req.organizadorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!evento.getAtivo() || !evento.getOrganizador().getAtivo() || !evento.getOrganizador().getUsuario().getAtivo()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (!evento.getOrganizador().getUsuario().getEmail().equals(auth.getName()) || !organizador.getUsuario().getEmail().equals(auth.getName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        evento.setCategoriaEvento(req.categoria());
        evento.setClassificacao(req.classificacao());
        evento.setTitulo(req.titulo());
        evento.setStatusEvento(req.status());
        evento.setOrganizador(organizador);

        return EventoMapper.toDTO(eventoRepository.save(evento));
    }

    public void deletarProprio(Long id, Authentication auth) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (evento.getOrganizador().getUsuario().getEmail().equals(auth.getName())) {
            eventoRepository.delete(evento);
        }
    }

    public void desativar(Long id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!evento.getAtivo()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        evento.setAtivo(false);
        eventoRepository.save(evento);
    }

}
