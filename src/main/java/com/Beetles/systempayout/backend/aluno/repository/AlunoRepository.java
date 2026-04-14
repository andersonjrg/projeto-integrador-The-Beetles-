package com.Beetles.systempayout.backend.aluno.repository;

import com.Beetles.systempayout.backend.aluno.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/*
Essa anotação faz o SpringBoot reconhecer essa Interface como um repositório
*/

@org.springframework.stereotype.Repository


//Aqui estou extendo o JPA que realiza a conexão com o banco de dados
public interface AlunoRepository extends JpaRepository<Aluno, UUID> {
    Optional<Aluno> findByTelefone(String telefone);
}
