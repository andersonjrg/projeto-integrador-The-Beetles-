package com.Beetles.systempayout.backend.historico.service;

import com.Beetles.systempayout.backend.aluno.model.Aluno;
import com.Beetles.systempayout.backend.aluno.repository.AlunoRepository;
import com.Beetles.systempayout.backend.historico.controller.Request.HistoricoRequest;
import com.Beetles.systempayout.backend.historico.controller.Response.HistoricoResponse;
import com.Beetles.systempayout.backend.historico.model.Historico;
import com.Beetles.systempayout.backend.historico.repository.HistoricoRepository;
import com.Beetles.systempayout.backend.shared.exception.IdNotFoundException;
import org.springframework.data.domain.Page;
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
                .orElseThrow(() -> new IdNotFoundException("Usuário não encontrado."));

        Historico historico = new Historico();
        historico.setAluno(aluno);
        historico.setValorCobrado(request.valor());

        historico.onCreated();
        return repository.save(historico);
    }

    @Transactional(readOnly = true)
    public Page<HistoricoResponse> verTodosHistoricos(Pageable pageable){
        return repository.findAll(pageable)
                .map(HistoricoResponse::toHistoricoResponse);
    }

    @Transactional(readOnly = true)
    public HistoricoResponse verHistoricoId(UUID id){
        return repository.findById(id)
                .map(HistoricoResponse::toHistoricoResponse)
                .orElseThrow(()-> new IdNotFoundException("Usuário não encontrado."));
    }

    @Transactional
    public Historico registrarDataDeConfirmacao(UUID id){
        Historico historico = repository.findById(id)
                .orElseThrow(()-> new IdNotFoundException("Pagamento não encontrado."));
        historico.dataConfirmation();
        repository.save(historico);
        return historico;
    }
    @Transactional(readOnly = true)
    public List<HistoricoResponse> getPagamentosAluno (UUID id){
        return repository.findByAlunoAlunoId(id)
                .stream()
                .map(HistoricoResponse::toHistoricoResponse)
                .toList();
    }
    public void deletarPagamentoId (UUID id){
        if(!repository.existsById(id)){
            throw new RuntimeException("Pagamento não encontrado.");
        }
        repository.deleteById(id);

    }
}
