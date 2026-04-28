package com.Beetles.systempayout.backend.admin.controller;

import com.Beetles.systempayout.backend.admin.controller.mapper.AdminMapper;
import com.Beetles.systempayout.backend.admin.controller.request.AdminRequest;
import com.Beetles.systempayout.backend.admin.controller.response.AdminResponse;
import com.Beetles.systempayout.backend.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService service;

    @PostMapping
    public ResponseEntity<AdminResponse> criar(AdminRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(AdminMapper.responseMapper(service.criar(AdminMapper.requestMapper(request))));
    }

    @GetMapping
    public ResponseEntity<AdminResponse> bucarPorEmail (@RequestBody String email){
        return ResponseEntity.status(HttpStatus.OK)
                .body(AdminMapper.responseMapper(service.buscarPorEmail(email)));
    }

    @DeleteMapping
    public void delete(@PathVariable UUID id){
        service.deletarAdmin(id);
    }
}
