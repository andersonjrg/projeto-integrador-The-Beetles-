package com.Beetles.SystemPayout.backEnd.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "planos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Planos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer planoId;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String categoria;
    @Column(nullable = false)
    private Integer frequenciaSemanal;
    private BigDecimal valor;
    private boolean ativo;
    private LocalDate dataCriacao;
}
