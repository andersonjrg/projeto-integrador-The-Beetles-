package com.Beetles.systempayout.backend.admin.controller;

import com.Beetles.systempayout.backend.admin.controller.response.AdminResponse;
import com.Beetles.systempayout.backend.admin.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("admin")
public class AdminController {
    private final AdminService service;

    public AdminController(AdminService service) {
        this.service = service;
    }

    @PostMapping("/findEmail/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminResponse> bucarPorEmail (@Valid @RequestBody String email){
        var adminEmail = service.buscarPorEmail(email);
        var response = AdminResponse.toAdminResponse(adminEmail);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        service.deletarAdmin(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
