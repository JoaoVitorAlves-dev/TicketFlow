package com.ticketflow.romeuzxg.service;

import com.ticketflow.romeuzxg.domain.Pedido;
import com.ticketflow.romeuzxg.domain.Roles.StatusPedido;
import com.ticketflow.romeuzxg.domain.Usuario;
import com.ticketflow.romeuzxg.dto.request.PedidoRequest;
import com.ticketflow.romeuzxg.dto.response.PedidoResponse;
import com.ticketflow.romeuzxg.mapper.PedidoMapper;
import com.ticketflow.romeuzxg.repository.PedidoRepository;
import com.ticketflow.romeuzxg.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {

    private final UsuarioRepository usuarioRepository;
    private final PedidoRepository pedidoRepository;

    public PedidoService(UsuarioRepository usuarioRepository, PedidoRepository pedidoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.pedidoRepository = pedidoRepository;
    }

    public Page<PedidoResponse> listar(Pageable pageable) {
        return pedidoRepository.findAll(pageable).map(PedidoMapper::toDTO);
    }

    @Transactional
    public PedidoResponse criar(PedidoRequest req) {
        Usuario usuario = usuarioRepository.findById(req.usuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Pedido entity = PedidoMapper.toEntity(req, usuario);

        entity.setStatusPedido(StatusPedido.PENDENTE);

        if (!usuario.getAtivo() || req.expiraEm().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        return PedidoMapper.toDTO(pedidoRepository.save(entity));
    }

    @Transactional
    public PedidoResponse atualizar(Long id, PedidoRequest req) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Usuario usuario = usuarioRepository.findById(req.usuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!pedido.getStatusPedido().equals(StatusPedido.PENDENTE)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        if (!pedido.getUsuario().equals(usuario) || pedido.getExpiraEm().isBefore(LocalDateTime.now()) || !usuario.getAtivo()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        pedido.setVersion(req.version());
        pedido.setTotal(req.total());
        pedido.setExpiraEm(req.expiraEm());
        pedido.setCupomCodigo(req.cupomCodigo());

        return PedidoMapper.toDTO(pedidoRepository.save(pedido));
    }

    @Transactional
    public void deletar(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        pedidoRepository.delete(pedido);
    }

    @Transactional
    public void scheduled() {
        List<Pedido> all = pedidoRepository.findAll();

        for (Pedido al: all) {
            if (al.getExpiraEm().isBefore(LocalDateTime.now())) {
                pedidoRepository.delete(al);
            }
        }
    }

}
