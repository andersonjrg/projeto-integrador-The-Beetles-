package com.Beetles.systempayout.backend.plano.controller;

import com.Beetles.systempayout.backend.plano.controller.mapper.PlanoMapper;
import com.Beetles.systempayout.backend.plano.controller.request.PlanoRequest;
import com.Beetles.systempayout.backend.plano.controller.response.PlanoResponse;
import com.Beetles.systempayout.backend.plano.model.Plano;
import com.Beetles.systempayout.backend.plano.service.PlanosService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PlanoMapper.mapResponse(service.criarPlano(PlanoMapper.mapRequest(request))));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<PlanoResponse>> mostrarPlanos(@RequestParam int paginas, @RequestParam int itens) {
        return ResponseEntity.ok(
                service.mostrarTodosPlanos(paginas, itens)
                        .stream()
                        .map(PlanoMapper::mapResponse)
                        .toList()
        );
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<PlanoResponse> verPlanoEspecifico(@PathVariable UUID id) {
        return ResponseEntity.ok(
                PlanoMapper.mapResponse(service.mostrarPlanoEspecificoPeloId(id))
        );
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<PlanoResponse> atualizarPlano(@Valid @RequestBody PlanoRequest plano, @PathVariable UUID id) {
        Plano planoAnterior = service.modificarPlano(PlanoMapper.mapRequest(plano), id);
        PlanoResponse planoAtualizado = PlanoMapper.mapResponse(planoAnterior);
        return new ResponseEntity<>(planoAtualizado, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> apagarPlano(@PathVariable UUID id){
        service.deletarPlano(id);
        return ResponseEntity.noContent().build();
    }
}