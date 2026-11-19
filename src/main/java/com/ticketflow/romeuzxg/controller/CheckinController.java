package com.ticketflow.romeuzxg.controller;

import com.ticketflow.romeuzxg.dto.request.CheckinRequest;
import com.ticketflow.romeuzxg.dto.response.CheckinResponse;
import com.ticketflow.romeuzxg.service.CheckinService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkin")
public class CheckinController {

    private final CheckinService checkinService;

    public CheckinController(CheckinService checkinService) {
        this.checkinService = checkinService;
    }

    @GetMapping("/viewrecords")
    public ResponseEntity<Page<CheckinResponse>> verReegistros(Pageable pageable, Authentication auth) {
        return ResponseEntity.ok(checkinService.verRegistros(pageable, auth));
    }

    @PostMapping("/createrecords")
    public ResponseEntity<CheckinResponse> criarRegistro(@RequestBody @Valid CheckinRequest req, Authentication auth) {
        return ResponseEntity.status(HttpStatus.CREATED).body(checkinService.criarRegistro(req, auth));
    }

    @GetMapping("/view")
    public ResponseEntity<Page<CheckinResponse>> listar(Pageable pageable) {
        return ResponseEntity.ok(checkinService.listar(pageable));
    }

    @PostMapping("/create")
    public ResponseEntity<CheckinResponse> criar(@RequestBody @Valid CheckinRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(checkinService.criar(req));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<CheckinResponse> atualizar(@PathVariable Long id, @RequestBody @Valid CheckinRequest req) {
        return ResponseEntity.ok(checkinService.atualizar(id, req));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        checkinService.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
