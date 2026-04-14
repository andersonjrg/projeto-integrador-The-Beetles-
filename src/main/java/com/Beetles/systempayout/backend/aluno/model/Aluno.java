package com.Beetles.systempayout.backend.aluno.model;

import com.Beetles.systempayout.backend.plano.model.Planos;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;




/*
Classe de criação de usuários normal, aqui é onde está a os atributos do usuário
*/

@Entity  //Essa anotação faz o SpringBoot reconhecer a Classe com uma entidade
@Table(name="alunos", uniqueConstraints = {
        @UniqueConstraint(columnNames = "telefone")
}) //Essa anotação ja cria uma tabela no banco de dados com o nome indicado, E o UniqueConstrains torna um atributo Unico(não pode ter um igual)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Aluno {
    /*
    Essa abaixo é uma geração automatica do Id do usuário
    */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "aluno_id",nullable = false, unique = true)//Não permite o atributo ser null
    private UUID alunoId;
    @Column(unique = true, nullable = false)
    private String telefone;
    @Column(nullable = false)
    private String nome;
    @ManyToOne
    @JoinColumn(name = "plano_id")
    private Planos planoEscolhidoId;
    private boolean primeiroAcesso = true;
    private String status;
    private LocalDateTime diaVencimento;
    private LocalDateTime dataProximoVencimento;
    @CreationTimestamp
    private LocalDateTime dataInicioPlano;
    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime dataCadastro;

    public void transformarTelefone() {
        if(this.telefone != null && !this.telefone.contains("@")) {
            this.telefone = this.telefone + "@ctjsfightuba.com.br";
        }
    }

    public void calcularVencimento(){
        if(this.dataInicioPlano == null){
            throw new RuntimeException("O usuário não possui um plano cadastrado");
        }else{
            diaVencimento = dataInicioPlano.plusMonths(1);
        }
    }
}
