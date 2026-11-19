package com.ticketflow.romeuzxg.controller;

import com.ticketflow.romeuzxg.dto.request.LoginRequest;
import com.ticketflow.romeuzxg.dto.request.RefreshTokenRequest;
import com.ticketflow.romeuzxg.dto.request.RegisterRequest;
import com.ticketflow.romeuzxg.dto.response.RefreshTokenResponse;
import com.ticketflow.romeuzxg.dto.response.UsuarioResponse;
import com.ticketflow.romeuzxg.service.UsuarioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Transactional
    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refresh(@RequestBody @Valid RefreshTokenRequest req) {
        return ResponseEntity.ok(usuarioService.refresh(req.accessToken()));
    }

    @Transactional
    @GetMapping("/view")
    public ResponseEntity<UsuarioResponse> visualizar(Authentication auth) {
        return ResponseEntity.ok(usuarioService.visualizar(auth));
    }

    @Transactional
    @PostMapping("/register")
    public ResponseEntity<UsuarioResponse> registrar(@RequestBody @Valid RegisterRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.registrar(req));
    }

    @Transactional
    @PostMapping("/login")
    public ResponseEntity<RefreshTokenResponse> logar(@RequestBody @Valid LoginRequest req) {
        return ResponseEntity.ok(usuarioService.login(req));
    }

    @Transactional
    @PatchMapping("/update/{id}")
    public ResponseEntity<Void> atualizarUsuario(@PathVariable UUID id, @RequestBody @Valid RegisterRequest req, Authentication auth) {
        usuarioService.atualizarUsuario(id, req, auth);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Transactional
    @DeleteMapping("/deactivate")
    public ResponseEntity<Void> desativar(Authentication auth) {
        usuarioService.desativar(auth);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
