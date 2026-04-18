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

@RestController
@RequestMapping("/alunos")

public class AlunoController {
    private final AlunoService service;

    public AlunoController(AlunoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AlunoResponse> saveUser(@RequestBody AlunoRequest request){
        Aluno alunoNovo = AlunoMapper.mapRequest(request);
        Aluno alunoSalvo = service.saveUser(alunoNovo);
        return new ResponseEntity<>(AlunoMapper.mapResponse(alunoSalvo), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AlunoResponse>> getAllUsers(){
        List<Aluno> alunos = service.listUsers();
        List<AlunoResponse> response = alunos.stream()
                .map(AlunoMapper::mapResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoResponse> getUserById(@PathVariable UUID id){
            Aluno aluno = service.listUserById(id);
            return ResponseEntity.ok(AlunoMapper.mapResponse(aluno));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoResponse> updateUser(@PathVariable UUID id, @RequestBody AlunoRequest request){
        Aluno aluno = AlunoMapper.mapRequest(request);
        Aluno response = service.updateUser(id, aluno);
        return ResponseEntity.ok(AlunoMapper.mapResponse(response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id){
            service.deleteUserById(id);
            return ResponseEntity.noContent().build();
        }
}
