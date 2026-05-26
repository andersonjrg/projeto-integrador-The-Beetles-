package com.Beetles.systempayout.backend.admin.service;

import com.Beetles.systempayout.backend.admin.controller.request.AdminRequest;
import com.Beetles.systempayout.backend.admin.controller.response.AdminResponse;
import com.Beetles.systempayout.backend.admin.model.Admin;
import com.Beetles.systempayout.backend.admin.repository.AdminRepository;
import com.Beetles.systempayout.backend.shared.exception.EmailNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
public class AdminService{

    private final AdminRepository repository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(AdminRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Admin registrar(AdminRequest request){
        Admin admin = new Admin();
        admin.setNome(request.nome());
        admin.setEmail(request.email());
        admin.setSenha(passwordEncoder.encode(request.senha()));
        return repository.save(admin);
    }

    @Transactional(readOnly = true)
    public AdminResponse buscarPorEmail(String email){
        return repository.findByEmail(email)
            .map(AdminResponse::toAdminResponse)
                .orElseThrow(()-> new EmailNotFoundException(email));
    }

    @Transactional
    public void deletarAdmin(UUID id){
        repository.deleteById(id);
    }
}
