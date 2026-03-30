package com.Beetles.SystemPayout.backEnd.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "historico_pagamento")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Historico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer historicoId;
    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private User historicoAlunoId;
    private BigDecimal valorCobrado;
    private String statusPagamento;
    private LocalDateTime dataSolicitacao;
    private LocalDateTime dataConfirmacao;

}