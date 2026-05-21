package com.Beetles.systempayout.backend.plano.controller;

import com.Beetles.systempayout.backend.plano.controller.request.PlanoRequest;
import com.Beetles.systempayout.backend.plano.controller.response.PlanoResponse;
import com.Beetles.systempayout.backend.plano.service.PlanosService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/plano")
public class PlanoController {

    private final PlanosService service;

    public PlanoController(PlanosService service) {
        this.service = service;
    }

    @PostMapping("/salvar")
    public ResponseEntity<PlanoResponse> salvarPlano(@Valid @RequestBody PlanoRequest request) {
        var plano = service.criarPlano(request);
        var response = PlanoResponse.toPlanoResponse(plano);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<PlanoResponse>> mostrarPlanos(@RequestParam Pageable pageable) {
        Page<PlanoResponse> response = service
                .mostrarTodosPlanos(pageable)
                .map(PlanoResponse::toPlanoResponse);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<PlanoResponse> verPlanoEspecifico(@PathVariable UUID id) {
        var plano = service.mostrarPlanoEspecificoPeloId(id);
        var response = PlanoResponse.toPlanoResponse(plano);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<PlanoResponse> atualizarPlano(@Valid @RequestBody PlanoRequest request, @PathVariable UUID id) {
        var plano = service.modificarPlano(request, id);
        var response = PlanoResponse.toPlanoResponse(plano);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> apagarPlano(@PathVariable UUID id){
        service.deletarPlano(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}