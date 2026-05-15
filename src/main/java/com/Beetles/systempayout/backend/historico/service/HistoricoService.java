package com.Beetles.systempayout.backend.historico.service;

import com.Beetles.systempayout.backend.historico.model.Historico;
import com.Beetles.systempayout.backend.historico.repository.HistoricoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class HistoricoService {

    private final HistoricoRepository repository;

    public HistoricoService(HistoricoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Historico salvarHistorico(Historico historico) {
        return repository.save(historico);
    }

    public List<Historico> verTodosHistoricos(int paginas, int itens){
        Pageable pageable = PageRequest.of(paginas, itens);
        Page<Historico> historicoPage = repository.findAll(pageable);
        return historicoPage.getContent();
    }

    public Historico verHistoricoId(UUID id){
        return repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Id não encontrado"));
    }

    @Transactional
    public Historico registrarDataDeSolicitacao(UUID id){
        Historico historico = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Id não encontrado"));
        historico.onCreated();
        return historico;
    }

    @Transactional
    public Historico registrarDataDeConfirmacao(UUID id){
        Historico historico = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Id não encontrado"));
        historico.dataConfirmation();
        return historico;
    }
}