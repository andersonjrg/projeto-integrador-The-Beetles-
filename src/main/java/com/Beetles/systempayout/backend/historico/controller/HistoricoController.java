package com.Beetles.systempayout.backend.historico.controller;

import com.Beetles.systempayout.backend.historico.controller.Mapper.HistoricoMapper;
import com.Beetles.systempayout.backend.historico.controller.Request.HistoricoRequest;
import com.Beetles.systempayout.backend.historico.controller.Response.HistoricoResponse;
import com.Beetles.systempayout.backend.historico.model.Historico;
import com.Beetles.systempayout.backend.historico.service.HistoricoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<HistoricoResponse> salvar(@RequestBody HistoricoRequest historico){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(HistoricoMapper.mapResponse(service.salvarHistorico(HistoricoMapper.mapRequest(historico))));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoricoResponse> buscar(@PathVariable UUID id){
        return ResponseEntity.ok(HistoricoMapper.mapResponse(service.verHistoricoId(id)));
        }

    @PostMapping("/solicitacao/{id}")
    public ResponseEntity<HistoricoResponse> solicitar(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(HistoricoMapper.mapResponse(service.registrarDataDeSolicitacao(id)));
    }

    @PostMapping("/confirmar/{id}")
    public ResponseEntity<HistoricoResponse> confirmar(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(HistoricoMapper.mapResponse(service.registrarDataDeConfirmacao(id)));
    }
}
