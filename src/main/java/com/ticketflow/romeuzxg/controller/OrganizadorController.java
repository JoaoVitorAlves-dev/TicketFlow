package com.ticketflow.romeuzxg.controller;

import com.ticketflow.romeuzxg.dto.request.OrganizadorRequest;
import com.ticketflow.romeuzxg.dto.response.OrganizadorResponse;
import com.ticketflow.romeuzxg.service.OrganizadorService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/organizer")
public class OrganizadorController {

    private final OrganizadorService organizadorService;

    public OrganizadorController(OrganizadorService organizadorService) {
        this.organizadorService = organizadorService;
    }

    @GetMapping("/view")
    public ResponseEntity<Page<OrganizadorResponse>> listar(Pageable pageable, Authentication auth) {
        return ResponseEntity.ok(organizadorService.listar(pageable, auth));
    }

    @PostMapping("/create")
    public ResponseEntity<OrganizadorResponse> criar(@RequestBody @Valid OrganizadorRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(organizadorService.criar(req));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<OrganizadorResponse> atualizar(@PathVariable Long id, @RequestBody @Valid OrganizadorRequest req) {
        return ResponseEntity.ok(organizadorService.atualizar(id, req));
    }

    @PatchMapping("/updaterecord/{id}")
    public ResponseEntity<OrganizadorResponse> atualizarConta(@PathVariable Long id, @RequestBody @Valid OrganizadorRequest req, Authentication auth) {
        return ResponseEntity.ok(organizadorService.atualizarConta(id, req, auth));
    }

    @DeleteMapping("/deactivate/{id}")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        organizadorService.desativar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
