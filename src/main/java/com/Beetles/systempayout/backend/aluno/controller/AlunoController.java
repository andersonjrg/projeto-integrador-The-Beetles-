package com.Beetles.systempayout.backend.aluno.controller;

import com.Beetles.systempayout.backend.aluno.DTO.AlunoDTO;
import com.Beetles.systempayout.backend.aluno.service.AlunoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/alunos")

public class AlunoController {
    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @PostMapping
    public ResponseEntity<AlunoDTO> saveUser(@RequestBody AlunoDTO alunoDTO){
            AlunoDTO savedAluno = alunoService.saveUser(alunoDTO);
            return new ResponseEntity<>(savedAluno, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AlunoDTO>> getAllUsers(){
            List<AlunoDTO> aluno = alunoService.showUsers();
            return ResponseEntity.ok(aluno);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoDTO> getUserById(@PathVariable UUID id){
            AlunoDTO aluno = alunoService.showUserById(id);
            if (aluno == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(aluno);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoDTO> updateUser(@PathVariable UUID id, @RequestBody AlunoDTO aluno){
            AlunoDTO alunoUp = alunoService.updateUser(id, aluno);
            return new ResponseEntity<>(alunoUp, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id){
            alunoService.deleteUserById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
}
