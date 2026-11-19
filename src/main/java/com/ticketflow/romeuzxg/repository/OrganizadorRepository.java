package com.ticketflow.romeuzxg.repository;

import com.ticketflow.romeuzxg.domain.Organizador;
import com.ticketflow.romeuzxg.dto.response.OrganizadorResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizadorRepository extends JpaRepository<Organizador, Long> {

    Page<OrganizadorResponse> findAllByUsuarioEmail(String email, Pageable pageable);

}
