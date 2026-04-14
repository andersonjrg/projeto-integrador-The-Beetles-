package com.Beetles.systempayout.backend.historico.DTO;

import com.Beetles.systempayout.backend.aluno.model.Aluno;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoricoDTO {
    private Aluno historicoAlunoId;
    private BigDecimal valorCobrado;
    private String statusPagamento;
    private LocalDate dataSolicitacao;
    private LocalDate dataConfirmacao;
}
