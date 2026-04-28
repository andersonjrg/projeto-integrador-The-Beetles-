package com.Beetles.systempayout.backend.admin.repository;

import com.Beetles.systempayout.backend.admin.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID> {

    Admin findByEmail(String email);
}
