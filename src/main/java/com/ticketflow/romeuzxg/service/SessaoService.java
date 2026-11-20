package com.ticketflow.romeuzxg.service;

import com.ticketflow.romeuzxg.domain.Evento;
import com.ticketflow.romeuzxg.domain.Local;
import com.ticketflow.romeuzxg.domain.Roles.StatusSessao;
import com.ticketflow.romeuzxg.domain.Sessao;
import com.ticketflow.romeuzxg.dto.request.SessaoRequest;
import com.ticketflow.romeuzxg.dto.response.SessaoResponse;
import com.ticketflow.romeuzxg.mapper.SessaoMapper;
import com.ticketflow.romeuzxg.repository.EventoRepository;
import com.ticketflow.romeuzxg.repository.LocalRepository;
import com.ticketflow.romeuzxg.repository.SessaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SessaoService {

    private final SessaoRepository sessaoRepository;
    private final EventoRepository eventoRepository;
    private final LocalRepository localRepository;

    public SessaoService(SessaoRepository sessaoRepository, EventoRepository eventoRepository, LocalRepository localRepository) {
        this.sessaoRepository = sessaoRepository;
        this.eventoRepository = eventoRepository;
        this.localRepository = localRepository;
    }

    @Transactional
    public Page<SessaoResponse> listar(Pageable pageable) {
        return sessaoRepository.findAll(pageable).map(SessaoMapper::toDTO);
    }

    @Transactional
    public SessaoResponse criarSessao(SessaoRequest req, Authentication auth) {
        Local local = localRepository.findById(req.localId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Evento evento = eventoRepository.findById(req.eventoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Sessao entity = SessaoMapper.toEntity(req, local, evento);

        if (!entity.getEvento().getOrganizador().getUsuario().getEmail().equals(auth.getName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        if (!entity.getEvento().getAtivo() || !entity.getEvento().getOrganizador().getAtivo()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (!req.status().equals(StatusSessao.ABERTA)) {
            throw new ResponseStatusException(HttpStatus.GONE);
        }

        return SessaoMapper.toDTO(sessaoRepository.save(entity));
    }

    @Transactional
    public SessaoResponse atualizarPropriaSessao(Long id, SessaoRequest req, Authentication auth) {
        Sessao sessao = sessaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Local local = localRepository.findById(req.localId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Evento evento = eventoRepository.findById(req.eventoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (sessao.getEvento().getAtivo() || sessao.getEvento().getOrganizador().getAtivo() || sessao.getEvento().getOrganizador().getUsuario().getAtivo()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (!sessao.getEvento().getOrganizador().getUsuario().getEmail().equals(auth.getName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        if (!evento.getOrganizador().getUsuario().getEmail().equals(auth.getName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        sessao.setStatus(req.status());
        sessao.setEvento(evento);
        sessao.setDataHora(req.dataHora());
        sessao.setLocal(local);

        return SessaoMapper.toDTO(sessaoRepository.save(sessao));
    }

    @Transactional
    public void deletarSessao(Long id, Authentication auth) {
        Sessao sessao = sessaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (sessao.getEvento().getOrganizador().getUsuario().getEmail().equals(auth.getName())) {
            sessaoRepository.delete(sessao);
        }
    }

    @Transactional
    public SessaoResponse criar(SessaoRequest req) {
        Local local = localRepository.findById(req.localId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Evento evento = eventoRepository.findById(req.eventoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Sessao entity = SessaoMapper.toEntity(req, local, evento);

        if (!entity.getEvento().getAtivo() || !entity.getEvento().getOrganizador().getAtivo()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (!req.status().equals(StatusSessao.ABERTA)) {
            throw new ResponseStatusException(HttpStatus.GONE);
        }

        return SessaoMapper.toDTO(sessaoRepository.save(entity));
    }

    @Transactional
    public SessaoResponse atualizar(Long id, SessaoRequest req) {
        Sessao sessao = sessaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Local local = localRepository.findById(req.localId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Evento evento = eventoRepository.findById(req.eventoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (sessao.getEvento().getAtivo() || sessao.getEvento().getOrganizador().getAtivo() || sessao.getEvento().getOrganizador().getUsuario().getAtivo()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        sessao.setStatus(req.status());
        sessao.setEvento(evento);
        sessao.setDataHora(req.dataHora());
        sessao.setLocal(local);

        return SessaoMapper.toDTO(sessaoRepository.save(sessao));
    }

    @Transactional
    public void deletarById(Long id) {
        Sessao sessao = sessaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        sessaoRepository.delete(sessao);
    }

    @Transactional
    public void deletar() {
        List<Sessao> all = sessaoRepository.findAll();
        for (Sessao sessao : all) {
            if (sessao.getDataHora().isBefore(LocalDateTime.now())) {
                sessaoRepository.delete(sessao);
            }
        }
    }
}
