package com.ticketflow.romeuzxg.service;

import com.ticketflow.romeuzxg.domain.Assento;
import com.ticketflow.romeuzxg.domain.Setor;
import com.ticketflow.romeuzxg.dto.request.AssentoRequest;
import com.ticketflow.romeuzxg.dto.response.AssentoResponse;
import com.ticketflow.romeuzxg.mapper.AssentoMapper;
import com.ticketflow.romeuzxg.repository.AssentoRepository;
import com.ticketflow.romeuzxg.repository.SetorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AssentoService {

    private final AssentoRepository assentoRepository;
    private final SetorRepository setorRepository;

    public AssentoService(AssentoRepository assentoRepository, SetorRepository setorRepository) {
        this.assentoRepository = assentoRepository;
        this.setorRepository = setorRepository;
    }

    public Page<AssentoResponse> listar(Pageable pageable) {
        return assentoRepository.findAll(pageable).map(AssentoMapper::toDTO);
    }

    public AssentoResponse criar(AssentoRequest req) {
        Setor setor = setorRepository.findById(req.setorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Assento entity = AssentoMapper.toEntity(req, setor);
        return AssentoMapper.toDTO(assentoRepository.save(entity));
    }

    public AssentoResponse atualizar(Long id, AssentoRequest req) {
        Assento assento = assentoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Setor setor = setorRepository.findById(req.setorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!assento.getAtivo()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        assento.setSetor(setor);
        assento.setNumero(req.numero());
        assento.setFila(req.fila());

        return AssentoMapper.toDTO(assentoRepository.save(assento));
    }

    public void desativar(Long id) {
        Assento assento = assentoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!assento.getAtivo()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        assento.setAtivo(false);
        assentoRepository.save(assento);
    }

}
