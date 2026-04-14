package com.Beetles.systempayout.backend.historico.controller;

import com.Beetles.systempayout.backend.historico.DTO.HistoricoDTO;
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
    public ResponseEntity<HistoricoDTO> salvar(@RequestBody HistoricoDTO historicoDTO){
        HistoricoDTO historicoSalvo = service.salvarHistorico(historicoDTO);
        return new ResponseEntity<>(historicoSalvo, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoricoDTO> buscar(@PathVariable UUID id){
        HistoricoDTO historicoBusca = service.verHistoricoId(id);
        return new ResponseEntity<>(historicoBusca, HttpStatus.FOUND);
    }

    @PostMapping("/solicitacao")
    public ResponseEntity<HistoricoDTO> solicitar(@PathVariable UUID id){
        HistoricoDTO historicoSolicitacao = service.registrarDataDeSolicitacao(id);
        return new ResponseEntity<>(historicoSolicitacao, HttpStatus.ACCEPTED);
    }

    @PostMapping("/confirmar")
    public ResponseEntity<HistoricoDTO> confirmar(@PathVariable UUID id){
        HistoricoDTO historicoConfirmacao = service.registrarDataDeConfirmacao(id);
        return new ResponseEntity<>(historicoConfirmacao, HttpStatus.ACCEPTED);
    }
}
