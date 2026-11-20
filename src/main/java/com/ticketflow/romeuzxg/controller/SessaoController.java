package com.ticketflow.romeuzxg.controller;

import com.ticketflow.romeuzxg.dto.request.SessaoRequest;
import com.ticketflow.romeuzxg.dto.response.SessaoResponse;
import com.ticketflow.romeuzxg.service.SessaoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/session")
public class SessaoController {

    private final SessaoService sessaoService;

    public SessaoController(SessaoService sessaoService) {
        this.sessaoService = sessaoService;
    }

    @GetMapping("/list")
    public ResponseEntity<Page<SessaoResponse>> listar(Pageable pageable) {
        return ResponseEntity.ok(sessaoService.listar(pageable));
    }

    @PostMapping("/createsession")
    public ResponseEntity<SessaoResponse> criarSessao(@RequestBody @Valid SessaoRequest req, Authentication auth) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sessaoService.criarSessao(req, auth));
    }

    @PatchMapping("/updateownsession/{id}")
    public ResponseEntity<SessaoResponse> atualizarPropriaSessao(@PathVariable Long id, @RequestBody @Valid SessaoRequest req, Authentication auth) {
        return ResponseEntity.ok(sessaoService.atualizarPropriaSessao(id, req, auth));
    }

    @DeleteMapping("/deletesession/{id}")
    public ResponseEntity<Void> deletarSessao(@PathVariable Long id, Authentication auth) {
        sessaoService.deletarSessao(id, auth);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/create")
    public ResponseEntity<SessaoResponse> criar(@RequestBody @Valid SessaoRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sessaoService.criar(req));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<SessaoResponse> atualizar(@PathVariable Long id, @RequestBody @Valid SessaoRequest req) {
        return ResponseEntity.ok(sessaoService.atualizar(id, req));
    }

    @DeleteMapping("/deletebyid/{id}")
    public ResponseEntity<Void> deletarById(@PathVariable Long id) {
        sessaoService.deletarById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
