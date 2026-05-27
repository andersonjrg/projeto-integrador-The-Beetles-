package com.Beetles.systempayout.backend.plano.model;

import com.Beetles.systempayout.backend.aluno.model.Aluno;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.Beetles.systempayout.backend.shared.utils.DateTimeLocal.pegarHorarioAtual;

@Entity
@Table(name = "planos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "alunos")
public class Plano {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, name = "plano_id")
    private UUID planoId;

    @Column(nullable = false)
    private String nome;

    @OneToMany(mappedBy = "planoEscolhidoId", fetch = FetchType.LAZY)
    private List<Aluno> alunos = new ArrayList<>();

    @Column(nullable = false)
    private String categoria;

    @Column
    private int frequenciaAulas;

    @Column(precision = 10, scale = 2)
    private BigDecimal valor;

    @Column
    private boolean ativo;

    @Column(updatable = false)
    private LocalDateTime dataCriacao;

    @PrePersist
    public void onCreated(){
        this.dataCriacao = pegarHorarioAtual();
    }
}
