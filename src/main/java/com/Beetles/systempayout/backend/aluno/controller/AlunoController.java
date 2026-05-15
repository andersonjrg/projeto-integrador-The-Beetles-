package com.Beetles.systempayout.backend.aluno.controller;

import com.Beetles.systempayout.backend.aluno.controller.mapper.AlunoMapper;
import com.Beetles.systempayout.backend.aluno.controller.request.AlunoRequest;
import com.Beetles.systempayout.backend.aluno.controller.response.AlunoResponse;
import com.Beetles.systempayout.backend.aluno.service.AlunoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("aluno")
@RequiredArgsConstructor
public class AlunoController {
    private final AlunoService service;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AlunoResponse>> getAllUsers(@RequestParam int paginas,@RequestParam int itens){
        return ResponseEntity.status(HttpStatus.OK).body(
                service.listUsers(paginas, itens)
                .stream()
                .map(AlunoMapper::mapResponse)
                .toList());
    }

    @GetMapping("/getId/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AlunoResponse> getUserById(@PathVariable UUID id){
            return ResponseEntity.ok(AlunoMapper.mapResponse(service.listUserById(id)));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AlunoResponse> updateUser(@PathVariable UUID id, @Valid @RequestBody AlunoRequest request){
        return ResponseEntity.ok(AlunoMapper.mapResponse(service.updateUser(id, AlunoMapper.mapRequest(request))));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id){
            service.deleteUserById(id);
            return ResponseEntity.noContent().build();
        }
}
