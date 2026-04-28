package com.Beetles.systempayout.backend.admin.service;

import com.Beetles.systempayout.backend.admin.model.Admin;
import com.Beetles.systempayout.backend.admin.repository.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class AdminService {

    AdminRepository repository;

    public AdminService(AdminRepository repository) {
        this.repository = repository;
    }

    public Admin criar(Admin admin){
        return repository.save(admin);
    }

    public Admin buscarPorEmail(String email){
        return repository.findByEmail(email);
    }

    public void deletarAdmin(UUID id){
        repository.deleteById(id);
    }
}
