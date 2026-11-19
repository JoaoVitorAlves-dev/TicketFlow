package com.ticketflow.romeuzxg.service;

import com.ticketflow.romeuzxg.domain.Checkin;
import com.ticketflow.romeuzxg.domain.Ingresso;
import com.ticketflow.romeuzxg.domain.Roles.StatusIngresso;
import com.ticketflow.romeuzxg.domain.Roles.StatusPedido;
import com.ticketflow.romeuzxg.dto.request.CheckinRequest;
import com.ticketflow.romeuzxg.dto.response.CheckinResponse;
import com.ticketflow.romeuzxg.mapper.CheckinMapper;
import com.ticketflow.romeuzxg.repository.CheckinRepository;
import com.ticketflow.romeuzxg.repository.IngressoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class CheckinService {

    private final CheckinRepository checkinRepository;
    private final IngressoRepository ingressoRepository;

    public CheckinService(CheckinRepository checkinRepository, IngressoRepository ingressoRepository) {
        this.checkinRepository = checkinRepository;
        this.ingressoRepository = ingressoRepository;
    }

    public Page<CheckinResponse> verRegistros(Pageable pageable, Authentication auth) {
        return checkinRepository.findAllByIngressoPedidoUsuarioEmail(auth.getName(), pageable);
    }

    public CheckinResponse criar(CheckinRequest req, Authentication auth) {
        Ingresso ingresso = ingressoRepository.findById(req.ingressoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!ingresso.getPedido().getUsuario().getAtivo() || !ingresso.getAssento().getAtivo()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        if (!ingresso.getPedido().getUsuario().getEmail().equals(auth.getName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        if (ingresso.getStatus().equals(StatusIngresso.CANCELADO) || ingresso.getStatus().equals(StatusIngresso.USADO)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        if (ingresso.getPedido().getExpiraEm().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        if (!ingresso.getPedido().getStatusPedido().equals(StatusPedido.PAGO)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        Checkin entity = CheckinMapper.toEntity(req, ingresso);

        return CheckinMapper.toDTO(checkinRepository.save(entity));
    }

    //adm
    public Page<CheckinResponse> listar(Pageable pageable) {
        return checkinRepository.findAll(pageable).map(CheckinMapper::toDTO);
    }

    public CheckinResponse criar(CheckinRequest req) {
        Ingresso ingresso = ingressoRepository.findById(req.ingressoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!ingresso.getPedido().getUsuario().getAtivo() || !ingresso.getAssento().getAtivo()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        if (ingresso.getStatus().equals(StatusIngresso.CANCELADO) || ingresso.getStatus().equals(StatusIngresso.USADO)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        if (ingresso.getPedido().getExpiraEm().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        if (!ingresso.getPedido().getStatusPedido().equals(StatusPedido.PAGO)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        Checkin entity = CheckinMapper.toEntity(req, ingresso);

        return CheckinMapper.toDTO(checkinRepository.save(entity));
    }

    public CheckinResponse atualizar(Long id, CheckinRequest req) {
        Checkin checkin = checkinRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!checkin.getIngresso().getPedido().getUsuario().getAtivo() || !checkin.getIngresso().getAssento().getAtivo()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        if (checkin.getIngresso().getStatus().equals(StatusIngresso.CANCELADO) || checkin.getIngresso().getStatus().equals(StatusIngresso.USADO)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        if (checkin.getIngresso().getPedido().getExpiraEm().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        if (!checkin.getIngresso().getPedido().getStatusPedido().equals(StatusPedido.PAGO)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        checkin.setPortaria(req.portaria());
        checkin.setFeitoEm(req.feitoEm());

        return CheckinMapper.toDTO(checkinRepository.save(checkin));
    }

    public void deletar(Long id) {
        Checkin checkin = checkinRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        checkinRepository.delete(checkin);
    }

}
