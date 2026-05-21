package com.Beetles.systempayout.backend.historico.model;

import com.Beetles.systempayout.backend.aluno.model.Aluno;
import com.Beetles.systempayout.backend.shared.enums.Enum_Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.Beetles.systempayout.backend.shared.utils.DateTimeLocal.pegarHorarioAtual;

@Entity
@Table(name = "historico_pagamento")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Historico {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "historico_id", nullable = false)
    private UUID historicoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno_id")
    @ToString.Exclude
    private Aluno aluno;

    @Column(precision = 10, scale = 2, nullable = false)
    @PositiveOrZero(message = "O valor cobrado não pode ser negativo")
    private BigDecimal valorCobrado;

    @Column(nullable = false)
    private Enum_Status statusPagamento;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataSolicitacao;

    @Column(updatable = false)
    private LocalDateTime dataConfirmacao;

    @PrePersist
    public void onCreated(){
        this.dataSolicitacao = pegarHorarioAtual();
        if(statusPagamento == null){
        this.statusPagamento = Enum_Status.PENDENTE;
        }
    }

    public void dataConfirmation(){
        this.dataConfirmacao = pegarHorarioAtual();
        this.statusPagamento = Enum_Status.PAGO;
    }
}