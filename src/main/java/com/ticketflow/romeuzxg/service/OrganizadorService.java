package com.ticketflow.romeuzxg.service;

import com.ticketflow.romeuzxg.domain.Organizador;
import com.ticketflow.romeuzxg.domain.Usuario;
import com.ticketflow.romeuzxg.dto.request.OrganizadorRequest;
import com.ticketflow.romeuzxg.dto.response.OrganizadorResponse;
import com.ticketflow.romeuzxg.mapper.OrganizadorMapper;
import com.ticketflow.romeuzxg.repository.OrganizadorRepository;
import com.ticketflow.romeuzxg.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class OrganizadorService {

    private final UsuarioRepository usuarioRepository;
    private final OrganizadorRepository organizadorRepository;

    public OrganizadorService(UsuarioRepository usuarioRepository, OrganizadorRepository organizadorRepository) {
        this.usuarioRepository = usuarioRepository;
        this.organizadorRepository = organizadorRepository;
    }

    public Page<OrganizadorResponse> listar(Pageable pageable, Authentication auth) {
        return organizadorRepository.findAllByUsuarioEmail(auth.getName(), pageable);
    }

    @Transactional
    public OrganizadorResponse criar(OrganizadorRequest req) {
        Usuario usuario = usuarioRepository.findById(req.usuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!usuario.getAtivo()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        Organizador entity = OrganizadorMapper.toEntity(req, usuario);
        return OrganizadorMapper.toDTO(organizadorRepository.save(entity));
    }

    @Transactional
    public OrganizadorResponse atualizar(Long id, OrganizadorRequest req) {
        Organizador organizador = organizadorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Usuario usuario = usuarioRepository.findById(req.usuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!organizador.getUsuario().equals(usuario) || !organizador.getUsuario().getAtivo() || !usuario.getAtivo()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        organizador.setNomeFantasia(req.nomeFantasia());
        organizador.setCnpj(req.cnpj());

        return OrganizadorMapper.toDTO(organizadorRepository.save(organizador));
    }

    @Transactional
    public OrganizadorResponse atualizarConta(Long id, OrganizadorRequest req, Authentication auth) {
        Organizador organizador = organizadorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Usuario usuario = usuarioRepository.findById(req.usuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!organizador.getUsuario().getEmail().equals(auth.getName()) || !usuario.getEmail().equals(auth.getName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        if (!usuario.getAtivo() || !organizador.getAtivo()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        organizador.setCnpj(req.cnpj());
        organizador.setNomeFantasia(req.nomeFantasia());

        return OrganizadorMapper.toDTO(organizadorRepository.save(organizador));
    }

    @Transactional
    public void desativar(Long id) {
        Organizador organizador = organizadorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!organizador.getAtivo() || !organizador.getUsuario().getAtivo()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        organizador.setAtivo(false);
        organizadorRepository.save(organizador);
    }


}
