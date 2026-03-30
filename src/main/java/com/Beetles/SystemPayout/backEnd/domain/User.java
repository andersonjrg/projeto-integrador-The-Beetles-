package com.Beetles.SystemPayout.backEnd.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;




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
public class User {
    /*
    Essa abaixo é uma geração automatica do Id do usuário
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aluno_id",nullable = false, unique = true)//Não permite o atributo ser null
    private Integer alunoId;
    @Column(unique = true, nullable = false)
    private String telefone;
    @Column(nullable = false)
    private String nome;
    @ManyToOne
    @JoinColumn(name = "plano_id")
    private Planos planoEscolhido;
    private boolean primeiroAcesso;
    private String status;
    private LocalDate diaVencimento;
    private LocalDate dataProximoVencimento;
    private LocalDate dataCadastro;

    public void transformarTelefone() {
        this.telefone = this.telefone + "@ctjsfightuba.com.br";
    }
}
