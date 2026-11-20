package com.ticketflow.romeuzxg.controller;

import com.ticketflow.romeuzxg.dto.request.AssentoRequest;
import com.ticketflow.romeuzxg.dto.response.AssentoResponse;
import com.ticketflow.romeuzxg.service.AssentoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seat")
public class AssentoController {

    private final AssentoService assentoService;

    public AssentoController(AssentoService assentoService) {
        this.assentoService = assentoService;
    }

    @GetMapping("/view")
    public ResponseEntity<Page<AssentoResponse>> listar(Pageable pageable) {
        return ResponseEntity.ok(assentoService.listar(pageable));
    }

    @PostMapping("/create")
    public ResponseEntity<AssentoResponse> criar(@RequestBody @Valid AssentoRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(assentoService.criar(req));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<AssentoResponse> atualizar(@PathVariable Long id, @RequestBody @Valid AssentoRequest req) {
        return ResponseEntity.ok(assentoService.atualizar(id, req));
    }

    @DeleteMapping("/deactivate/{id}")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        assentoService.desativar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
