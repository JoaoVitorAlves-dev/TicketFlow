package com.ticketflow.romeuzxg.mapper;

import com.ticketflow.romeuzxg.domain.Local;
import com.ticketflow.romeuzxg.domain.Setor;
import com.ticketflow.romeuzxg.dto.request.SetorRequest;
import com.ticketflow.romeuzxg.dto.response.SetorResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SetorMapper {

    public Setor toEntity(SetorRequest req, Local local) {
        Setor setor = new Setor();
        setor.setLocal(local);
        setor.setNome(req.nome());
        setor.setPrecoBase(req.precoBase());
        return setor;
    }

    public SetorResponse toDTO(Setor setor) {
        return new SetorResponse(
            setor.getId(), setor.getLocal(), setor.getNome(), setor.getPrecoBase()
        );
    }

}
