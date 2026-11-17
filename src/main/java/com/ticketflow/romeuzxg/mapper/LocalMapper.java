package com.ticketflow.romeuzxg.mapper;

import com.ticketflow.romeuzxg.domain.Local;
import com.ticketflow.romeuzxg.dto.request.LocalRequest;
import com.ticketflow.romeuzxg.dto.response.LocalResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LocalMapper {

    public Local toEntity(LocalRequest req) {
        Local local = new Local();
        local.setCapacidade(req.capacidade());
        local.setCidade(req.cidade());
        local.setEstado(req.estado());
        local.setNome(req.nome());
        return local;
    }

    public LocalResponse toDTO(Local local) {
        return new LocalResponse(
                local.getId(), local.getNome(), local.getCidade(), local.getEstado(), local.getCapacidade()
        );
    }

}
