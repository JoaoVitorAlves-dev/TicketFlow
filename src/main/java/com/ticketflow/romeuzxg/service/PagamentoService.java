package com.ticketflow.romeuzxg.service;

import com.ticketflow.romeuzxg.domain.Local;
import com.ticketflow.romeuzxg.domain.Pagamento;
import com.ticketflow.romeuzxg.domain.Pedido;
import com.ticketflow.romeuzxg.domain.Roles.StatusPagamento;
import com.ticketflow.romeuzxg.dto.request.PagamentoRequest;
import com.ticketflow.romeuzxg.dto.response.PagamentoResponse;
import com.ticketflow.romeuzxg.mapper.PagamentoMapper;
import com.ticketflow.romeuzxg.repository.PagamentoRepository;
import com.ticketflow.romeuzxg.repository.PedidoRepository;
import org.springframework.boot.micrometer.observation.autoconfigure.ObservationProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final PedidoRepository pedidoRepository;

    public PagamentoService(PagamentoRepository pagamentoRepository, PedidoRepository pedidoRepository) {
        this.pagamentoRepository = pagamentoRepository;
        this.pedidoRepository = pedidoRepository;
    }

    public Page<PagamentoResponse> verRegistros(Pageable pageable, Authentication auth) {
        return pagamentoRepository.findAllByPedidoUsuarioEmail(auth.getName(), pageable);
    }

    public PagamentoResponse criarProprioPagamento(PagamentoRequest req, Authentication auth) {
        Pedido pedido = pedidoRepository.findById(req.pedidoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!pedido.getUsuario().getAtivo()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        if (!pedido.getUsuario().getEmail().equals(auth.getName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        if (pedido.getExpiraEm().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_CONTENT);
        }

        Pagamento entity = PagamentoMapper.toEntity(req, pedido);
        entity.setStatus(StatusPagamento.PENDENTE);
        entity.setIdempotencyKey(UUID.randomUUID().toString());

        return PagamentoMapper.toDTO(pagamentoRepository.save(entity));
    }

    //adm
    public Page<PagamentoResponse> listarTudo(Pageable pageable) {
        return pagamentoRepository.findAll(pageable).map(PagamentoMapper::toDTO);
    }

    public PagamentoResponse criar(PagamentoRequest req) {
        Pedido pedido = pedidoRepository.findById(req.pedidoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!pedido.getUsuario().getAtivo()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        if (pedido.getExpiraEm().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_CONTENT);
        }

        Pagamento entity = PagamentoMapper.toEntity(req, pedido);
        entity.setStatus(StatusPagamento.PENDENTE);
        entity.setIdempotencyKey(UUID.randomUUID().toString());

        return PagamentoMapper.toDTO(pagamentoRepository.save(entity));
    }

    public PagamentoResponse atualizar(Long id, PagamentoRequest req) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!pagamento.getStatus().equals(StatusPagamento.PENDENTE)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        if (pagamento.getPedido().getExpiraEm().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_CONTENT);
        }
        if (!pagamento.getPedido().getUsuario().getAtivo()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        Pedido pedido = pedidoRepository.findById(req.pedidoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!pedido.getUsuario().getAtivo()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        if (pedido.getExpiraEm().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_CONTENT);
        }

        pagamento.setMetodo(req.metodo());

        return PagamentoMapper.toDTO(pagamentoRepository.save(pagamento));
    }

    public void deletar(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (pagamento.getStatus().equals(StatusPagamento.APROVADO)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        pagamentoRepository.delete(pagamento);
    }

    //scheduler
    public void scheduler() {
        List<Pagamento> all = pagamentoRepository.findAll();

        for (Pagamento pagamento: all) {
            if (!pagamento.getPedido().getUsuario().getAtivo() || !pagamento.getStatus().equals(StatusPagamento.PENDENTE)) {
                pagamentoRepository.delete(pagamento);
            }
        }
    }

}
