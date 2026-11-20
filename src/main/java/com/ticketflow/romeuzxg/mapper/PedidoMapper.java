package com.ticketflow.romeuzxg.mapper;

import com.ticketflow.romeuzxg.domain.Pedido;
import com.ticketflow.romeuzxg.domain.Usuario;
import com.ticketflow.romeuzxg.dto.request.PedidoRequest;
import com.ticketflow.romeuzxg.dto.response.PedidoResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PedidoMapper {

    public Pedido toEntity(PedidoRequest req, Usuario usuario) {
        Pedido pedido = new Pedido();
        pedido.setCupomCodigo(req.cupomCodigo());
        pedido.setExpiraEm(req.expiraEm());
        pedido.setTotal(req.total());
        pedido.setUsuario(usuario);
        pedido.setVersion(req.version());
        return pedido;
    }

    public PedidoResponse toDTO(Pedido pedido) {
        return new PedidoResponse(
                pedido.getId(), pedido.getUsuario().getId(), pedido.getStatusPedido(), pedido.getTotal(),
                pedido.getCupomCodigo(), pedido.getExpiraEm(), pedido.getVersion()
        );
    }

}
