package com.Beetles.systempayout.backend.aluno.service;

import com.Beetles.systempayout.backend.aluno.controller.request.AlunoRequest;
import com.Beetles.systempayout.backend.aluno.controller.response.AlunoResponse;
import com.Beetles.systempayout.backend.aluno.model.Aluno;
import com.Beetles.systempayout.backend.aluno.repository.AlunoRepository;
import com.Beetles.systempayout.backend.historico.repository.HistoricoRepository;
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
    private final HistoricoRepository historicoRepository;

    public AlunoService(AlunoRepository repository,
                        PlanoRepository planoRepository,
                        HistoricoRepository historicoRepository) {
        this.repository = repository;
        this.planoRepository = planoRepository;
        this.historicoRepository = historicoRepository;
    }

    @Transactional(readOnly = true)
    public Page<AlunoResponse> listUsers(Pageable pageable) {
        return repository.findAll(pageable)
                .map(AlunoResponse::toAlunoResponse);
    }


    @Transactional(readOnly = true)
    public AlunoResponse listUserById(UUID id) {
        return repository.findById(id)
                .map(AlunoResponse::toAlunoResponse)
                .orElseThrow(()-> new IdNotFoundException("Usuário não encontrado."));
    }

    @Transactional
    public Aluno registerUser(AlunoRequest request) {

        Plano plano = null;
        if (request.plano() != null) {
            plano = planoRepository.findById(request.plano())
                    .orElseThrow(() -> new IdNotFoundException("Plano não encontrado."));
        }


        Aluno aluno = new Aluno();
        aluno.setNome(request.nome());
        aluno.setPlanoEscolhidoId(plano);
        aluno.setStatus(request.status());

        if (request.dataInicioPlano() != null) {
            aluno.setDataInicioPlano(request.dataInicioPlano());
            aluno.calcularVencimento();
        }

        return repository.save(aluno);
    }


    @Transactional
    public void deleteUserById(UUID id) {
        if (!repository.existsById(id)) {
            throw new IdNotFoundException("Usuário não encontrado.");
        }
        historicoRepository.deleteByAlunoAlunoId(id);
        repository.deleteById(id);
    }

    @Transactional
    public Aluno updateUser(UUID id, AlunoRequest request) {
        Aluno aluno = repository.findById(id)
            .orElseThrow(() -> new IdNotFoundException("Usuário não encontrado."));

        Plano plano = planoRepository.findById(request.plano())
                .orElseThrow(() -> new IdNotFoundException("Plano não encontrado."));

        if (request.nome() != null) {
            aluno.setNome(request.nome());
        }
        if (request.status() != null){
            aluno.setStatus(request.status());
        }
        aluno.setPlanoEscolhidoId(plano);

        if (request.dataInicioPlano() != null) {
            aluno.setDataInicioPlano(request.dataInicioPlano());
            aluno.calcularVencimento();
        }

        return repository.save(aluno);

    }
}