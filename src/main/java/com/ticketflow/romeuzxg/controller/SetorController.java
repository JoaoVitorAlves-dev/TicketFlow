package com.ticketflow.romeuzxg.controller;

import com.ticketflow.romeuzxg.dto.request.SetorRequest;
import com.ticketflow.romeuzxg.dto.response.SetorResponse;
import com.ticketflow.romeuzxg.service.SetorService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/setor")
public class SetorController {

    private final SetorService setorService;

    public SetorController(SetorService setorService) {
        this.setorService = setorService;
    }

    @GetMapping("/list")
    public ResponseEntity<Page<SetorResponse>> listar(Pageable pageable) {
        return ResponseEntity.ok(setorService.listar(pageable));
    }

    @PostMapping("/create")
    public ResponseEntity<SetorResponse> criar(@RequestBody @Valid SetorRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(setorService.criar(req));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<SetorResponse> atualizar(@PathVariable Long id, @RequestBody @Valid SetorRequest req) {
        return ResponseEntity.ok(setorService.atualizar(id, req));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        setorService.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
