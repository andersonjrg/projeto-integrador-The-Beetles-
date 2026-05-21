package com.Beetles.systempayout.backend.aluno.controller;

import com.Beetles.systempayout.backend.aluno.controller.request.AlunoRequest;
import com.Beetles.systempayout.backend.aluno.controller.response.AlunoResponse;
import com.Beetles.systempayout.backend.aluno.service.AlunoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("aluno")
public class AlunoController {
    private final AlunoService service;

    public AlunoController(AlunoService service) {
        this.service = service;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<AlunoResponse>> getAllUsers(@RequestParam Pageable pageable){
        Page<AlunoResponse> response = service
                .listUsers(pageable)
                .map(AlunoResponse::toAlunoResponse);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/getId/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AlunoResponse> getUserById(@PathVariable UUID id){
        var request = service.listUserById(id);
        var response = AlunoResponse.toAlunoResponse(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AlunoResponse> updateUser(@PathVariable UUID id, @Valid @RequestBody AlunoRequest request){
        var aluno = service.updateUser(id, request);
        var response = AlunoResponse.toAlunoResponse(aluno);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id){
            service.deleteUserById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
}
