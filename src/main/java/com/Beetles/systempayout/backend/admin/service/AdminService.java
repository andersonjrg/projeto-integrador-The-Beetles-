package com.Beetles.systempayout.backend.admin.service;

import com.Beetles.systempayout.backend.admin.controller.request.AdminRequest;
import com.Beetles.systempayout.backend.admin.model.Admin;
import com.Beetles.systempayout.backend.admin.repository.AdminRepository;
import com.Beetles.systempayout.backend.shared.exception.EmailNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AdminService{

    private final AdminRepository repository;
    private final PasswordEncoder passwordEncoder;

    public Admin registrar(AdminRequest request){
        Admin admin = new Admin();
        admin.setNome(request.nome());
        admin.setEmail(request.email());
        admin.setSenha(request.senha());
        return repository.save(admin);
    }

    public Admin buscarPorEmail(String email){
        return repository.findByEmail(email)
                .orElseThrow(()-> new EmailNotFoundException(email));
    }

    public void deletarAdmin(UUID id){
        repository.deleteById(id);
    }
}
