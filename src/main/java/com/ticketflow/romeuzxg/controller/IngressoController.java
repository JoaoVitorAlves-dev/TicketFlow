package com.ticketflow.romeuzxg.controller;

import com.ticketflow.romeuzxg.dto.request.IngressoRequest;
import com.ticketflow.romeuzxg.dto.response.IngressoResponse;
import com.ticketflow.romeuzxg.service.IngressoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ticket")
public class IngressoController {

    private final IngressoService ingressoService;

    public IngressoController(IngressoService ingressoService) {
        this.ingressoService = ingressoService;
    }

    @GetMapping("/view")
    public ResponseEntity<Page<IngressoResponse>> listar(Pageable pageable) {
        return ResponseEntity.ok(ingressoService.listar(pageable));
    }

    @GetMapping("/viewrecords")
    public ResponseEntity<IngressoResponse> verRegistros(Authentication auth) {
        return ResponseEntity.ok(ingressoService.verRegistroProprio(auth));
    }

    @GetMapping("/{id}/qrcode")
    public ResponseEntity<byte[]> gerarCodigoQr(@PathVariable Long id) {
        byte[] bytes = ingressoService.gerarQrCode(id);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytes);
    }

    @PostMapping("/create")
    public ResponseEntity<IngressoResponse> criar(@RequestBody @Valid IngressoRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ingressoService.criar(req));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<IngressoResponse> atualizar(@PathVariable Long id, @RequestBody @Valid IngressoRequest req) {
        return ResponseEntity.ok(ingressoService.atualizar(id, req));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        ingressoService.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
