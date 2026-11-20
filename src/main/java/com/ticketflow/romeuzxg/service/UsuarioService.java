package com.ticketflow.romeuzxg.service;

import com.ticketflow.romeuzxg.domain.RefreshToken;
import com.ticketflow.romeuzxg.domain.Roles.Role;
import com.ticketflow.romeuzxg.domain.Usuario;
import com.ticketflow.romeuzxg.dto.request.LoginRequest;
import com.ticketflow.romeuzxg.dto.request.RegisterRequest;
import com.ticketflow.romeuzxg.dto.response.RefreshTokenResponse;
import com.ticketflow.romeuzxg.dto.response.UsuarioResponse;
import com.ticketflow.romeuzxg.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenService tokenService, RefreshTokenService refreshTokenService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.refreshTokenService = refreshTokenService;
    }

    @Transactional
    public RefreshTokenResponse refresh(String access) {
        RefreshToken refreshToken = refreshTokenService.validarERotacionar(access);

        String token = tokenService.gerarToken(refreshToken.getUsuario().getEmail());

        return new RefreshTokenResponse(token, refreshToken.getToken());
    }

    @Transactional
    public UsuarioResponse visualizar(Authentication auth) {
        Usuario usuario = usuarioRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return new UsuarioResponse(usuario.getId(), usuario.getNome(), usuario.getCpf(), usuario.getEmail());
    }

    @Transactional
    public UsuarioResponse registrar(RegisterRequest req) {
        if (usuarioRepository.existsByCpf(req.cpf()) || usuarioRepository.existsByEmail(req.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        Usuario usuario = new Usuario();
        usuario.setNome(req.nome());
        usuario.setCpf(req.cpf());
        usuario.setEmail(req.email());
        usuario.setPassword(passwordEncoder.encode(req.password()));
        usuario.setRole(Role.CUSTOMER);

        usuarioRepository.save(usuario);

        return new UsuarioResponse(usuario.getId(), usuario.getNome(), usuario.getCpf(), usuario.getEmail());
    }

    @Transactional
    public RefreshTokenResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));

        Usuario usuario = usuarioRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        String token = tokenService.gerarToken(usuario.getEmail());
        String refresh = refreshTokenService.criar(usuario).getToken();

        return new RefreshTokenResponse(token, refresh);
    }

    @Transactional
    public void atualizarUsuario(UUID id, RegisterRequest req, Authentication auth) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!usuario.getEmail().equals(auth.getName()) || !req.email().equals(auth.getName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        usuario.setNome(req.nome());
        usuario.setPassword(passwordEncoder.encode(req.password()));
        usuario.setEmail(req.email());
        usuario.setCpf(req.cpf());

        usuarioRepository.save(usuario);
    }

    @Transactional
    public void desativar(Authentication auth) {
        Usuario usuario = usuarioRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!usuario.getEmail().equals(auth.getName()) || !usuario.getAtivo()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        usuario.setAtivo(false);
        usuarioRepository.save(usuario);
    }

}
