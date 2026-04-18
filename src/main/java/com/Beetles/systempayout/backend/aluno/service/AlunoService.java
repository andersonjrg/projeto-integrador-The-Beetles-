package com.Beetles.systempayout.backend.aluno.service;

import com.Beetles.systempayout.backend.aluno.model.Aluno;
import com.Beetles.systempayout.backend.aluno.repository.AlunoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@org.springframework.stereotype.Service

public class AlunoService {

   private final AlunoRepository repository;

    public AlunoService(AlunoRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Aluno> listUsers() {
        return repository.findAll();
    }


    @Transactional(readOnly = true)
    public Aluno listUserById(UUID id) {
        return repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Id não encontrado"));
    }


    @Transactional
    public Aluno saveUser(Aluno alunoSalvo) {
        return repository.save(alunoSalvo);
    }


    @Transactional
    public void deleteUserById(UUID id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Não foi possível deletar o Usuário: Id invalido");
        }
        repository.deleteById(id);
    }

    @Transactional
    public Aluno updateUser(UUID id, Aluno aluno) {
        Aluno alunoExist = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Não foi possível atualizar o Usuário: Id inválido"));

        if (aluno.getNome() != null) {
            alunoExist.setNome(aluno.getNome());
        }
        if (aluno.getEmail() != null) {
            alunoExist.setEmail(aluno.getEmail());
        }
        if (aluno.getPlanoEscolhidoId() != null) {
            alunoExist.setPlanoEscolhidoId(aluno.getPlanoEscolhidoId());
        }
        return repository.save(alunoExist);

    }
}