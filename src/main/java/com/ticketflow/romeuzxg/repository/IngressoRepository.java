package com.ticketflow.romeuzxg.repository;

import com.ticketflow.romeuzxg.domain.Ingresso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngressoRepository extends JpaRepository<Ingresso, Long> {

    Optional<Ingresso> findByPedidoUsuarioEmail(String email);

}
