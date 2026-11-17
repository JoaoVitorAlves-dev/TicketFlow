package com.ticketflow.romeuzxg.mapper;

import com.ticketflow.romeuzxg.domain.Evento;
import com.ticketflow.romeuzxg.domain.Local;
import com.ticketflow.romeuzxg.domain.Sessao;
import com.ticketflow.romeuzxg.dto.request.SessaoRequest;
import com.ticketflow.romeuzxg.dto.response.SessaoResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SessaoMapper {

    public Sessao toEntity(SessaoRequest req, Local local, Evento evento) {
        Sessao sessao = new Sessao();
        sessao.setLocal(local);
        sessao.setDataHora(req.dataHora());
        sessao.setEvento(evento);
        sessao.setStatus(req.status());
        return sessao;
    }

    public SessaoResponse toDTO(Sessao sessao) {
        return new SessaoResponse(
                sessao.getId(), sessao.getLocal(), sessao.getDataHora(), sessao.getStatus()
        );
    }

}
