package com.Beetles.systempayout.backend.historico.controller;

import com.Beetles.systempayout.backend.historico.controller.Mapper.HistoricoMapper;
import com.Beetles.systempayout.backend.historico.controller.Request.HistoricoRequest;
import com.Beetles.systempayout.backend.historico.controller.Response.HistoricoResponse;
import com.Beetles.systempayout.backend.historico.service.HistoricoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<HistoricoResponse> salvar(@Valid @RequestBody HistoricoRequest historico){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(HistoricoMapper.mapResponse(service.salvarHistorico(HistoricoMapper.mapRequest(historico))));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HistoricoResponse> buscar(@PathVariable UUID id){
        return ResponseEntity.ok(HistoricoMapper.mapResponse(service.verHistoricoId(id)));
    }

    @GetMapping("/findAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<HistoricoResponse>> buscarAll(@RequestParam int paginas, @RequestParam int itens){
        return ResponseEntity.status(HttpStatus.OK).body(
                service.verTodosHistoricos(paginas, itens)
                        .stream()
                        .map(HistoricoMapper::mapResponse)
                        .toList());
    }

    @PostMapping("/solicitacao/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HistoricoResponse> solicitar(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(HistoricoMapper.mapResponse(service.registrarDataDeSolicitacao(id)));
    }

    @PostMapping("/confirmar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HistoricoResponse> confirmar(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(HistoricoMapper.mapResponse(service.registrarDataDeConfirmacao(id)));
    }
}
