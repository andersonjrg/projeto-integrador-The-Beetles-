package com.Beetles.systempayout.backend.aluno.controller;

import com.Beetles.systempayout.backend.aluno.controller.mapper.AlunoMapper;
import com.Beetles.systempayout.backend.aluno.controller.request.AlunoRequest;
import com.Beetles.systempayout.backend.aluno.controller.response.AlunoResponse;
import com.Beetles.systempayout.backend.aluno.model.Aluno;
import com.Beetles.systempayout.backend.aluno.service.AlunoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/alunos")

public class AlunoController {
    private final AlunoService service;

    public AlunoController(AlunoService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<AlunoResponse> saveUser(@RequestBody AlunoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(AlunoMapper.mapResponse((service.saveUser(AlunoMapper.mapRequest(request)))));
    }

    @GetMapping("/all")
    public ResponseEntity<List<AlunoResponse>> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(
                service.listUsers()
                .stream()
                .map(AlunoMapper::mapResponse)
                .toList());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<AlunoResponse> getUserById(@PathVariable UUID id){
            return ResponseEntity.ok(AlunoMapper.mapResponse(service.listUserById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoResponse> updateUser(@PathVariable UUID id, @RequestBody AlunoRequest request){
        return ResponseEntity.ok(AlunoMapper.mapResponse(service.updateUser(id, AlunoMapper.mapRequest(request))));
    }

    @DeleteMapping("deletar/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id){
            service.deleteUserById(id);
            return ResponseEntity.noContent().build();
        }
}
