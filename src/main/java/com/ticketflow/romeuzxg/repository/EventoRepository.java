package com.ticketflow.romeuzxg.repository;

import com.ticketflow.romeuzxg.domain.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
}
