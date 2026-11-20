package com.ticketflow.romeuzxg.service;

import com.ticketflow.romeuzxg.domain.Assento;
import com.ticketflow.romeuzxg.domain.Ingresso;
import com.ticketflow.romeuzxg.domain.Pedido;
import com.ticketflow.romeuzxg.domain.Roles.StatusPedido;
import com.ticketflow.romeuzxg.domain.Roles.StatusSessao;
import com.ticketflow.romeuzxg.domain.Sessao;
import com.ticketflow.romeuzxg.dto.request.IngressoRequest;
import com.ticketflow.romeuzxg.dto.response.IngressoResponse;
import com.ticketflow.romeuzxg.mapper.IngressoMapper;
import com.ticketflow.romeuzxg.repository.AssentoRepository;
import com.ticketflow.romeuzxg.repository.IngressoRepository;
import com.ticketflow.romeuzxg.repository.PedidoRepository;
import com.ticketflow.romeuzxg.repository.SessaoRepository;
import com.ticketflow.romeuzxg.service.qrcode.QrCodeService;
import jakarta.transaction.Transactional;
import org.springframework.boot.micrometer.observation.autoconfigure.ObservationProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class IngressoService {

    private final IngressoRepository ingressoRepository;
    private final PedidoRepository pedidoRepository;
    private final SessaoRepository sessaoRepository;
    private final AssentoRepository assentoRepository;
    private final QrCodeService qrCodeService;

    public IngressoService(IngressoRepository ingressoRepository, PedidoRepository pedidoRepository, SessaoRepository sessaoRepository, AssentoRepository assentoRepository, QrCodeService qrCodeService) {
        this.ingressoRepository = ingressoRepository;
        this.pedidoRepository = pedidoRepository;
        this.sessaoRepository = sessaoRepository;
        this.assentoRepository = assentoRepository;
        this.qrCodeService = qrCodeService;
    }

    @Transactional
    public Page<IngressoResponse> listar(Pageable pageable) {
        return ingressoRepository.findAll(pageable).map(IngressoMapper::toDTO);
    }

    public IngressoResponse verRegistroProprio(Authentication auth) {
        Ingresso ingresso = ingressoRepository.findByPedidoUsuarioEmail(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return new IngressoResponse(ingresso.getId(), ingresso.getPedido(), ingresso.getSessao(), ingresso.getAssento(), ingresso.getCodigoQr(), ingresso.getStatus());
    }

    public byte[] gerarQrCode(Long id) {
        Ingresso ingresso = ingressoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        try {
            return qrCodeService.gerarCodigoQr(ingresso.getCodigoQr(), 300, 300);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao gerar QR Code");
        }

    }


    @Transactional
    public IngressoResponse criar(IngressoRequest req) {
        Pedido pedido = pedidoRepository.findById(req.pedidoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Sessao sessao = sessaoRepository.findById(req.assentoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Assento assento = assentoRepository.findById(req.assentoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Ingresso entity = IngressoMapper.toEntity(req, assento, pedido, sessao);

        if (!entity.getAssento().getAtivo() || !entity.getPedido().getUsuario().getAtivo() || entity.getPedido().getExpiraEm().isBefore(LocalDateTime.now())
                || entity.getSessao().getEvento().getAtivo() || entity.getSessao().getEvento().getOrganizador().getAtivo()
                || entity.getSessao().getEvento().getOrganizador().getUsuario().getAtivo() || entity.getSessao().getDataHora().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        if (!entity.getSessao().getStatus().equals(StatusSessao.ABERTA) || !entity.getPedido().getStatusPedido().equals(StatusPedido.PENDENTE)) {
            throw new ResponseStatusException(HttpStatus.GONE);
        }

        return IngressoMapper.toDTO(ingressoRepository.save(entity));
    }

    @Transactional
    public IngressoResponse atualizar(Long id, IngressoRequest req) {
        Ingresso ingresso = ingressoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Assento assento = assentoRepository.findById(req.assentoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Pedido pedido = pedidoRepository.findById(req.assentoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Sessao sessao = sessaoRepository.findById(req.assentoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!ingresso.getPedido().getUsuario().equals(pedido.getUsuario())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        if (assento.getAtivo() || !pedido.getUsuario().getAtivo()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        if (!sessao.getEvento().getAtivo() || !sessao.getEvento().getOrganizador().getUsuario().getAtivo() || !sessao.getEvento().getOrganizador().getAtivo()) {
            throw new ResponseStatusException(HttpStatus.GONE);
        }
        if (!ingresso.getAssento().getAtivo() || !ingresso.getPedido().getUsuario().getAtivo() || ingresso.getPedido().getExpiraEm().isBefore(LocalDateTime.now())
                || ingresso.getSessao().getEvento().getAtivo() || ingresso.getSessao().getEvento().getOrganizador().getAtivo()
                || ingresso.getSessao().getEvento().getOrganizador().getUsuario().getAtivo() || ingresso.getSessao().getDataHora().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        if (!ingresso.getSessao().getStatus().equals(StatusSessao.ABERTA) || !ingresso.getPedido().getStatusPedido().equals(StatusPedido.PENDENTE)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        ingresso.setStatus(req.status());
        ingresso.setCodigoQr(UUID.randomUUID());
        ingresso.setSessao(sessao);
        ingresso.setPedido(pedido);
        ingresso.setAssento(assento);

        return IngressoMapper.toDTO(ingressoRepository.save(ingresso));
    }

    @Transactional
    public void deletar(Long id) {
        Ingresso ingresso = ingressoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        ingressoRepository.delete(ingresso);
    }

}
