package com.ticketflow.romeuzxg.controller;

import com.ticketflow.romeuzxg.dto.request.LocalRequest;
import com.ticketflow.romeuzxg.dto.response.LocalResponse;
import com.ticketflow.romeuzxg.service.LocalService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/local")
public class LocalController {

    private final LocalService localService;

    public LocalController(LocalService localService) {
        this.localService = localService;
    }

    @GetMapping("/view")
    public ResponseEntity<Page<LocalResponse>> listar(Pageable pageable) {
        return ResponseEntity.ok(localService.listar(pageable));
    }

    @PostMapping("/create")
    public ResponseEntity<LocalResponse> criar(@RequestBody @Valid LocalRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(localService.criar(req));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<LocalResponse> atualizar(@PathVariable Long id, @RequestBody @Valid LocalRequest req) {
        return ResponseEntity.ok(localService.atualizar(id, req));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        localService.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
