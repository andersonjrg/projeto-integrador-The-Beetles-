package com.Beetles.systempayout.backend.aluno.repository;

import com.Beetles.systempayout.backend.aluno.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface AlunoRepository extends JpaRepository<Aluno, UUID> {
    List<Aluno> findByPlanoEscolhidoIdPlanoId(UUID planoId);
}
