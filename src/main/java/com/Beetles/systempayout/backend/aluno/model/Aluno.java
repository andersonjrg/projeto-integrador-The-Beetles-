package com.Beetles.systempayout.backend.aluno.model;

import com.Beetles.systempayout.backend.plano.model.Plano;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="alunos")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "aluno_id",nullable = false, unique = true)
    private UUID alunoId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String numero;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, length = 20)
    private String senha;

    @ManyToOne
    @JoinColumn(name = "plano_escolhido_id", nullable = true)
    private Plano planoEscolhidoId;

    private boolean primeiroAcesso = true;

    private String status;

    private LocalDateTime diaVencimento;

    private LocalDateTime dataProximoVencimento;

    @CreationTimestamp
    private LocalDateTime dataInicioPlano;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime dataCadastro;

    public void calcularVencimento(){
        if(this.dataInicioPlano == null){
            throw new RuntimeException("O usuário não possui um plano cadastrado");
        }else{
            diaVencimento = dataInicioPlano.plusMonths(1);
        }
    }
    public void proximoVencimento(){
        this.dataProximoVencimento = this.diaVencimento.plusMonths(1);
    }
}
