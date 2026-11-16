package com.ticketflow.romeuzxg.repository;

import com.ticketflow.romeuzxg.domain.RefreshToken;
import com.ticketflow.romeuzxg.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

    Boolean existsByToken(String token);

    void deleteByUsuario(Usuario usuario);

}
