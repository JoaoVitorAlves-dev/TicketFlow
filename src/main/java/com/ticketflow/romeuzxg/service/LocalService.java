package com.ticketflow.romeuzxg.service;

import com.ticketflow.romeuzxg.domain.Local;
import com.ticketflow.romeuzxg.dto.request.LocalRequest;
import com.ticketflow.romeuzxg.dto.response.LocalResponse;
import com.ticketflow.romeuzxg.mapper.LocalMapper;
import com.ticketflow.romeuzxg.repository.LocalRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class LocalService {

    private final LocalRepository localRepository;

    public LocalService(LocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    public Page<LocalResponse> listar(Pageable pageable) {
        return localRepository.findAll(pageable).map(LocalMapper::toDTO);
    }

    public LocalResponse criar(LocalRequest localRequest) {
        Local entity = LocalMapper.toEntity(localRequest);
        return LocalMapper.toDTO(localRepository.save(entity));
    }

    public LocalResponse atualizar(Long id, LocalRequest req) {
        Local local = localRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        local.setNome(req.nome());
        local.setEstado(req.estado());
        local.setCidade(req.cidade());
        local.setCapacidade(req.capacidade());
        return LocalMapper.toDTO(localRepository.save(local));
    }

    public void deletar(Long id) {
        Local local = localRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        localRepository.delete(local);
    }

}
