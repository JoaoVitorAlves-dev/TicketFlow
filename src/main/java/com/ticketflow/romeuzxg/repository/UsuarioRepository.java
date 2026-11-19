package com.ticketflow.romeuzxg.repository;

import com.ticketflow.romeuzxg.domain.Usuario;
import com.ticketflow.romeuzxg.dto.response.UsuarioResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    Boolean existsByEmail(String email);

    Boolean existsByCpf(String cpf);

    Optional<Usuario> findByEmail(String email);

}
