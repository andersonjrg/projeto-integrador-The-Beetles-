package com.Beetles.systempayout.backend.aluno.service;

import com.Beetles.systempayout.backend.aluno.controller.request.AlunoRequest;
import com.Beetles.systempayout.backend.aluno.model.Aluno;
import com.Beetles.systempayout.backend.aluno.repository.AlunoRepository;
import com.Beetles.systempayout.backend.plano.model.Plano;
import com.Beetles.systempayout.backend.plano.repository.PlanoRepository;
import com.Beetles.systempayout.backend.shared.exception.IdNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AlunoService{

    private final AlunoRepository repository;
    private final PlanoRepository planoRepository;

    public AlunoService(AlunoRepository repository, PlanoRepository planoRepository) {
        this.repository = repository;
        this.planoRepository = planoRepository;
    }

    @Transactional(readOnly = true)
    public Page<Aluno> listUsers(Pageable pageable) {
        return repository.findAll(pageable);
    }


    @Transactional(readOnly = true)
    public Aluno listUserById(UUID id) {
        return repository.findById(id)
                .orElseThrow(()-> new IdNotFoundException(id));
    }

    @Transactional
    public Aluno registerUser(AlunoRequest request) {

        Plano plano = null;
        if (request.plano() != null) {
            plano = planoRepository.findById(request.plano())
                    .orElseThrow(() -> new IdNotFoundException(request.plano()));
        }

        Aluno aluno = new Aluno();
        aluno.setNome(request.nome());
        aluno.setPlanoEscolhidoId(plano);
        aluno.setStatus(request.status());

        return repository.save(aluno);
    }


    @Transactional
    public void deleteUserById(UUID id) {
        if (!repository.existsById(id)) {
            throw new IdNotFoundException(id);
        }
        repository.deleteById(id);
    }

    @Transactional
    public Aluno updateUser(UUID id, AlunoRequest request) {
        Aluno aluno = repository.findById(id)
            .orElseThrow(() -> new IdNotFoundException(id));

        Plano plano = planoRepository.findById(request.plano())
                .orElseThrow(() -> new IdNotFoundException(request.plano()));

        if (request.nome() != null) {
            aluno.setNome(request.nome());
        }
        if (request.status() != null){
            aluno.setStatus(request.status());
        }
        aluno.setPlanoEscolhidoId(plano);
        return repository.save(aluno);

    }
}