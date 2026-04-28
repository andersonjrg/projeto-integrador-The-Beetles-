package com.Beetles.systempayout.backend.aluno.repository;

import com.Beetles.systempayout.backend.aluno.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


@org.springframework.stereotype.Repository


public interface AlunoRepository extends JpaRepository<Aluno, UUID> {
    Optional<Aluno> findByEmail(String email);
}
