package com.ticketflow.romeuzxg.repository;

import com.ticketflow.romeuzxg.domain.Pagamento;
import com.ticketflow.romeuzxg.dto.response.PagamentoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    Page<PagamentoResponse> findAllByPedidoUsuarioEmail(String email, Pageable pageable);

}
