package com.ticketflow.romeuzxg.repository;

import com.ticketflow.romeuzxg.domain.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findAllByUsuarioEmail(String email);

    Page<Pedido> findAllByUsuarioEmail(String email, Pageable pageable);

}
