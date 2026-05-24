package com.Beetles.systempayout.backend.historico.controller;

import com.Beetles.systempayout.backend.historico.controller.Request.HistoricoRequest;
import com.Beetles.systempayout.backend.historico.controller.Response.HistoricoResponse;
import com.Beetles.systempayout.backend.historico.service.HistoricoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/historico")
public class HistoricoController {
    private final HistoricoService service;

    public HistoricoController(HistoricoService service) {
        this.service = service;
    }

    @PostMapping("/salvar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HistoricoResponse> salvar(@Valid @RequestBody HistoricoRequest request){
        var historico = service.salvarHistorico(request);
        var response = HistoricoResponse.toHistoricoResponse(historico);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HistoricoResponse> buscar(@PathVariable UUID id){
        var response = service.verHistoricoId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/findAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<HistoricoResponse>> buscarAll(@RequestParam Pageable pageable){
        var response = service.verTodosHistoricos(pageable);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/solicitacao/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HistoricoResponse> solicitar(@PathVariable UUID id){
        var historico = service.registrarDataDeSolicitacao(id);
        var response = HistoricoResponse.toHistoricoResponse(historico);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/confirmar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HistoricoResponse> confirmar(@PathVariable UUID id){
        var historico = service.registrarDataDeConfirmacao(id);
        var response = HistoricoResponse.toHistoricoResponse(historico);
        return ResponseEntity.ok(response);
    }
}
