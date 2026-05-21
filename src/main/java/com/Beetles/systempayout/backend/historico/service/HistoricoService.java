package com.Beetles.systempayout.backend.historico.service;

import com.Beetles.systempayout.backend.aluno.model.Aluno;
import com.Beetles.systempayout.backend.aluno.repository.AlunoRepository;
import com.Beetles.systempayout.backend.historico.controller.Request.HistoricoRequest;
import com.Beetles.systempayout.backend.historico.model.Historico;
import com.Beetles.systempayout.backend.historico.repository.HistoricoRepository;
import com.Beetles.systempayout.backend.shared.exception.IdNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class HistoricoService {

    private final AlunoRepository alunoRepository;
    private final HistoricoRepository repository;

    public HistoricoService(HistoricoRepository repository, AlunoRepository alunoRepository) {
        this.repository = repository;
        this.alunoRepository = alunoRepository;
    }

    @Transactional
    public Historico salvarHistorico(HistoricoRequest request) {
        Aluno aluno = alunoRepository.findById(request.alunoId())
                .orElseThrow(() -> new IdNotFoundException(request.alunoId()));

        Historico historico = new Historico();
        historico.setAluno(aluno);
        historico.setValorCobrado(request.valor());

        return repository.save(historico);
    }

    public Page<Historico> verTodosHistoricos(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Historico verHistoricoId(UUID id){
        return repository.findById(id)
                .orElseThrow(()-> new IdNotFoundException(id));
    }

    @Transactional
    public Historico registrarDataDeSolicitacao(UUID id){
        Historico historico = repository.findById(id)
                .orElseThrow(()-> new IdNotFoundException(id));
        historico.onCreated();
        return historico;
    }

    @Transactional
    public Historico registrarDataDeConfirmacao(UUID id){
        Historico historico = repository.findById(id)
                .orElseThrow(()-> new IdNotFoundException(id));
        historico.dataConfirmation();
        return historico;
    }
}