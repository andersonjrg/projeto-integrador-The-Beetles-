package com.Beetles.SystemPayout.backEnd.entity;

import jakarta.persistence.*;
import org.jspecify.annotations.NonNull;


/*
Classe de criação de usuários normal, aqui é onde está a os atributos do usuário
*/

@Entity  //Essa anotação faz o SpringBoot reconhecer a Classe com uma entidade
@Table(name="NormalUsers") //Essa anotação ja cria uma tabela no banco de dados com o nome indicado
public class User {
    /*
    Essa abaixo é uma geração automatica do Id do usuário
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InformationUsers")
    @NonNull
    private Long id;
    private String name;
    private String email;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
