package com.Beetles.systempayout.backend.historico.controller.Response;

import com.Beetles.systempayout.backend.historico.model.Historico;
import com.Beetles.systempayout.backend.shared.enums.Enum_Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record HistoricoResponse(UUID id,
                                String nomeAluno,
                                BigDecimal valorCobrado,
                                Enum_Status statusPagamento,
                                LocalDateTime dataSolicitacao,
                                LocalDateTime dataConfirmacao) {

    public static HistoricoResponse toHistoricoResponse(Historico historico){
        return new HistoricoResponse(
                historico.getHistoricoId(),
                historico.getAluno().getNome(),
                historico.getValorCobrado(),
                historico.getStatusPagamento(),
                historico.getDataSolicitacao(),
                historico.getDataConfirmacao()
        );
    }
}
