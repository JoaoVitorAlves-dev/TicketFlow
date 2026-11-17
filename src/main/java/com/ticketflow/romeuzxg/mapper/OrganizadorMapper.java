package com.ticketflow.romeuzxg.mapper;

import com.ticketflow.romeuzxg.domain.Organizador;
import com.ticketflow.romeuzxg.domain.Usuario;
import com.ticketflow.romeuzxg.dto.request.OrganizadorRequest;
import com.ticketflow.romeuzxg.dto.response.OrganizadorResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OrganizadorMapper {

    public Organizador toEntity(OrganizadorRequest req, Usuario usuario) {
        Organizador organizador = new Organizador();
        organizador.setAtivo(req.ativo());
        organizador.setCnpj(req.cnpj());
        organizador.setNomeFantasia(req.nomeFantasia());
        organizador.setUsuario(usuario);
        return organizador;
    }

    public OrganizadorResponse toDTO(Organizador org) {
        return new OrganizadorResponse(
                org.getId(), org.getUsuario().getNome(), org.getNomeFantasia(), org.getCnpj(), org.getAtivo()
        );
    }

}
