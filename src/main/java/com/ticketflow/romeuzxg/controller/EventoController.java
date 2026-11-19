package com.ticketflow.romeuzxg.controller;

import com.ticketflow.romeuzxg.dto.request.EventoRequest;
import com.ticketflow.romeuzxg.dto.response.EventoResponse;
import com.ticketflow.romeuzxg.service.EventoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/evento")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping("/view")
    public ResponseEntity<Page<EventoResponse>> listar(Pageable pageable) {
        return ResponseEntity.ok(eventoService.listar(pageable));
    }

    @PostMapping("/create")
    public ResponseEntity<EventoResponse> criar(@RequestBody @Valid EventoRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoService.criar(req));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<EventoResponse> atualizar(@PathVariable Long id, @RequestBody @Valid EventoRequest req) {
        return ResponseEntity.ok(eventoService.atualizar(id, req));
    }

    @DeleteMapping("/deactivate/{id}")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        eventoService.desativar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/updaterecords/{id}")
    public ResponseEntity<EventoResponse> atualizarProprio(@PathVariable Long id, @RequestBody @Valid EventoRequest req, Authentication auth) {
        return ResponseEntity.ok(eventoService.atualizarProprio(id, req, auth));
    }

    @DeleteMapping("/deleterecords/{id}")
    public ResponseEntity<Void> deletarProprio(@PathVariable Long id, Authentication auth) {
        eventoService.deletarProprio(id, auth);
        return ResponseEntity.noContent().build();
    }

}
