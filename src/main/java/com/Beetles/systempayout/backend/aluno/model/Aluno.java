package com.Beetles.systempayout.backend.aluno.model;

import com.Beetles.systempayout.backend.plano.model.Plano;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;




/*
Classe de criação de usuários normal, aqui é onde está a os atributos do usuário
*/

@Entity  //Essa anotação faz o SpringBoot reconhecer a Classe com uma entidade
@Table(name="alunos", uniqueConstraints = {
        @UniqueConstraint(columnNames = "telefone")
}) //Essa anotação ja cria uma tabela no banco de dados com o nome indicado, E o UniqueConstrains torna um atributo Unico(não pode ter um igual)
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Aluno {
    /*
    Essa abaixo é uma geração automatica do Id do usuário
    */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "aluno_id",nullable = false, unique = true)//Não permite o atributo ser null
    private UUID alunoId;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String senha;
    @Column(nullable = false)
    private String nome;
    @ManyToOne
    @JoinColumn(name = "plano_id")
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
        LocalDateTime vencimentoProximo = this.diaVencimento.plusMonths(1);
    }
}
