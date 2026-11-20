package com.ticketflow.romeuzxg.controller;

import com.ticketflow.romeuzxg.dto.request.PagamentoRequest;
import com.ticketflow.romeuzxg.dto.response.PagamentoResponse;
import com.ticketflow.romeuzxg.service.PagamentoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class PagamentoController {

    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @GetMapping("/viewrecords")
    public ResponseEntity<Page<PagamentoResponse>> verRegistros(Pageable pageable, Authentication auth) {
        return ResponseEntity.ok(pagamentoService.verRegistros(pageable, auth));
    }

    @PostMapping("/createcustompayment")
    public ResponseEntity<PagamentoResponse> criarProprioPagamento(@RequestBody @Valid PagamentoRequest req, Authentication auth) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pagamentoService.criarProprioPagamento(req, auth));
    }

    @GetMapping("/listeverythcriarProprioPagamentoing")
    public ResponseEntity<Page<PagamentoResponse>> listarTudo(Pageable pageable) {
        return ResponseEntity.ok(pagamentoService.listarTudo(pageable));
    }

    @PostMapping("/create")
    public ResponseEntity<PagamentoResponse> criar(@RequestBody @Valid PagamentoRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pagamentoService.criar(req));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<PagamentoResponse> atualizar(@PathVariable Long id, @RequestBody @Valid PagamentoRequest req) {
        return ResponseEntity.ok(pagamentoService.atualizar(id, req));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pagamentoService.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
