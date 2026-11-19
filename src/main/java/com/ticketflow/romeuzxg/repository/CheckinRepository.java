package com.ticketflow.romeuzxg.repository;

import com.ticketflow.romeuzxg.domain.Checkin;
import com.ticketflow.romeuzxg.dto.response.CheckinResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckinRepository extends JpaRepository<Checkin, Long> {

    Page<CheckinResponse> findAllByIngressoPedidoUsuarioEmail(String email, Pageable pageable);

}
