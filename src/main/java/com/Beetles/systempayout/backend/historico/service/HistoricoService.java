package com.Beetles.systempayout.backend.historico.service;

import com.Beetles.systempayout.backend.historico.DTO.HistoricoDTO;
import com.Beetles.systempayout.backend.historico.mapper.HistoricoMapper;
import com.Beetles.systempayout.backend.historico.model.Historico;
import com.Beetles.systempayout.backend.historico.repository.HistoricoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.Beetles.systempayout.backend.shared.utils.DateTimeLocal.pegarHorarioAtual;

@Service
public class HistoricoService {

    private final HistoricoRepository historicoRepository;
    private final HistoricoMapper historicoMapper;

    public HistoricoService(HistoricoRepository historicoRepository, HistoricoMapper historicoMapper) {
        this.historicoRepository = historicoRepository;
        this.historicoMapper = historicoMapper;
    }

    @Transactional
    public HistoricoDTO salvarHistorico(HistoricoDTO historicoDTO) {
        Historico historico = historicoMapper.map(historicoDTO);
        historicoRepository.save(historico);
        return historicoMapper.map(historico);
    }

    public HistoricoDTO verHistoricoId(UUID id){
        Historico historico = historicoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Id não encontrado"));
        return historicoMapper.map(historico);
    }

    @Transactional
    public HistoricoDTO registrarDataDeSolicitacao(UUID id){
        Historico historico = historicoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Histórico não encontrado"));
        if(historico.getDataSolicitacao() != null){
            throw new RuntimeException("A Solicitação desse historico ja foi criada");
        }
        historico.setDataSolicitacao(pegarHorarioAtual());
        return historicoMapper.map(historicoRepository.save(historico));
    }

    @Transactional
    public HistoricoDTO registrarDataDeConfirmacao(UUID id){
        Historico historico = historicoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Id não encontrado"));
        if(historico.getDataConfirmacao() != null){
            throw new RuntimeException("Esse histórico ja foi Confirmado");
        }
        historico.setDataConfirmacao(pegarHorarioAtual());
        historicoRepository.save(historico);
        return historicoMapper.map(historico);
    }
}