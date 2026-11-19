package com.ticketflow.romeuzxg.service;

import com.ticketflow.romeuzxg.domain.Local;
import com.ticketflow.romeuzxg.domain.Setor;
import com.ticketflow.romeuzxg.dto.request.SetorRequest;
import com.ticketflow.romeuzxg.dto.response.SetorResponse;
import com.ticketflow.romeuzxg.mapper.SetorMapper;
import com.ticketflow.romeuzxg.repository.LocalRepository;
import com.ticketflow.romeuzxg.repository.SetorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SetorService {

    private final SetorRepository setorRepository;
    private final LocalRepository localRepository;

    public SetorService(SetorRepository setorRepository, LocalRepository localRepository) {
        this.setorRepository = setorRepository;
        this.localRepository = localRepository;
    }

    public Page<SetorResponse> listar(Pageable pageable) {
        return setorRepository.findAll(pageable).map(SetorMapper::toDTO);
    }

    public SetorResponse criar(SetorRequest req) {
        Local local = localRepository.findById(req.localId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Setor entity = SetorMapper.toEntity(req, local);
        return SetorMapper.toDTO(setorRepository.save(entity));
    }

    public SetorResponse atualizar(Long id, SetorRequest req) {
        Setor setor = setorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Local local = localRepository.findById(req.localId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        setor.setNome(req.nome());
        setor.setPrecoBase(req.precoBase());
        setor.setLocal(local);

        return SetorMapper.toDTO(setorRepository.save(setor));
    }

    public void deletar(Long id) {
        Setor setor = setorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        setorRepository.delete(setor);
    }

}
