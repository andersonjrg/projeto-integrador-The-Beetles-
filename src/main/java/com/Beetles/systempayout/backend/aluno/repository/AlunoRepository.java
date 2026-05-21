package com.Beetles.systempayout.backend.aluno.repository;

import com.Beetles.systempayout.backend.aluno.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface AlunoRepository extends JpaRepository<Aluno, UUID> {
    Optional<UserDetails> findByEmail(String email);
}
