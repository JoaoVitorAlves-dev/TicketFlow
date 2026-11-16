package com.ticketflow.romeuzxg.repository;

import com.ticketflow.romeuzxg.domain.Organizador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizadorRepository extends JpaRepository<Organizador, Long> {
}
