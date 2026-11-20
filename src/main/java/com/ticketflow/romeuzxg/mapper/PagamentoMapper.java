package com.ticketflow.romeuzxg.mapper;

import com.ticketflow.romeuzxg.domain.Pagamento;
import com.ticketflow.romeuzxg.domain.Pedido;
import com.ticketflow.romeuzxg.dto.request.PagamentoRequest;
import com.ticketflow.romeuzxg.dto.response.PagamentoResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PagamentoMapper {

    public Pagamento toEntity(PagamentoRequest req, Pedido pedido) {
        Pagamento pagamento = new Pagamento();
        pagamento.setMetodo(req.metodo());
        pagamento.setPedido(pedido);
        return pagamento;
    }

    public PagamentoResponse toDTO(Pagamento pg) {
        return new PagamentoResponse(
                pg.getId(), pg.getPedido(), pg.getMetodo(), pg.getStatus(), pg.getIdempotencyKey()
        );
    }

}
