package com.ticketflow.romeuzxg.repository;

import com.ticketflow.romeuzxg.domain.Ingresso;
import com.ticketflow.romeuzxg.domain.Roles.StatusIngresso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngressoRepository extends JpaRepository<Ingresso, Long> {

    Optional<Ingresso> findByPedidoUsuarioEmail(String email);

    boolean existsBySessaoIdAndAssentoIdAndStatusNot(
            Long sessaoId, Long assentoId, StatusIngresso status);

    boolean existsBySessaoIdAndAssentoIdAndStatusNotAndIdNot(
            Long sessaoId, Long assentoId, StatusIngresso status, Long id);

}
