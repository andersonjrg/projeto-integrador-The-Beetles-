package com.Beetles.systempayout.backend.historico.model;

import com.Beetles.systempayout.backend.aluno.model.Aluno;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    @Column(nullable = false, unique = true)
    private UUID historicoId;
    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal valorCobrado;
    @Column(nullable = false)
    private String statusPagamento;
    @Column(nullable = false, updatable = false)
    private LocalDate dataSolicitacao;
    private LocalDate dataConfirmacao;

    @PrePersist
    public void onCreated(){
        this.dataSolicitacao = pegarHorarioAtual();
        if (statusPagamento == null){
            this.statusPagamento = "PENDENTE";
        }
    }
}