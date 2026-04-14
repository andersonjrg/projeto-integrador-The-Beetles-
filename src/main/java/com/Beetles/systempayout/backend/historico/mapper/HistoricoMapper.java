package com.Beetles.systempayout.backend.historico.mapper;

import com.Beetles.systempayout.backend.historico.DTO.HistoricoDTO;
import com.Beetles.systempayout.backend.historico.model.Historico;
import org.springframework.stereotype.Component;

@Component
public class HistoricoMapper {
    public Historico map(HistoricoDTO historicoDTO){
        Historico historico = new Historico();
        historico.setValorCobrado(historicoDTO.getValorCobrado());
        historico.setStatusPagamento(historicoDTO.getStatusPagamento());
        historico.setDataSolicitacao(historicoDTO.getDataSolicitacao());
        historico.setDataConfirmacao(historicoDTO.getDataConfirmacao());
        return historico;
    }
    public HistoricoDTO map(Historico historico){
        HistoricoDTO historicoDTO = new HistoricoDTO();
        historicoDTO.setValorCobrado(historico.getValorCobrado());
        historicoDTO.setStatusPagamento(historico.getStatusPagamento());
        historicoDTO.setDataSolicitacao(historico.getDataSolicitacao());
        historicoDTO.setDataConfirmacao(historico.getDataConfirmacao());
        return historicoDTO;
    }
}
