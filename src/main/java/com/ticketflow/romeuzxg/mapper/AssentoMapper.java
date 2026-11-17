package com.ticketflow.romeuzxg.mapper;

import com.ticketflow.romeuzxg.domain.Assento;
import com.ticketflow.romeuzxg.domain.Setor;
import com.ticketflow.romeuzxg.dto.request.AssentoRequest;
import com.ticketflow.romeuzxg.dto.response.AssentoResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AssentoMapper {

    public Assento toEntity(AssentoRequest req, Setor setor) {
        Assento assento = new Assento();
        assento.setAtivo(req.ativo());
        assento.setFila(req.fila());
        assento.setNumero(req.numero());
        assento.setSetor(setor);
        return assento;
    }

    public AssentoResponse toDTO(Assento assento) {
        return new AssentoResponse(
                assento.getId(), assento.getSetor(), assento.getFila(), assento.getNumero(), assento.getAtivo()
        );
    }

}
