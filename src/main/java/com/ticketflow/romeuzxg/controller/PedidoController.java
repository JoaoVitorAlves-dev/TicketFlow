package com.ticketflow.romeuzxg.controller;

import com.ticketflow.romeuzxg.dto.request.PedidoRequest;
import com.ticketflow.romeuzxg.dto.response.PedidoResponse;
import com.ticketflow.romeuzxg.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping("/listown")
    public ResponseEntity<Page<PedidoResponse>> listarProprio(Authentication auth, Pageable pageable) {
        return ResponseEntity.ok(pedidoService.listarProprios(auth, pageable));
    }

    @GetMapping("/list")
    public ResponseEntity<Page<PedidoResponse>> listar(Pageable pageable) {
        return ResponseEntity.ok(pedidoService.listar(pageable));
    }

    @PostMapping("/createorder")
    public ResponseEntity<PedidoResponse> criarPedido(@RequestBody @Valid PedidoRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.criarPedido(req));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<PedidoResponse> atualizar(@PathVariable Long id, @RequestBody @Valid PedidoRequest req) {
        return ResponseEntity.ok(pedidoService.atualizar(id, req));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pedidoService.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/deleterecords/{id}")
    public ResponseEntity<Void> deletarRegistros(@PathVariable Long id, Authentication auth) {
        pedidoService.deletarRegistros(id, auth);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
