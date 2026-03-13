package com.Beetles.SystemPayout.backEnd.repository;

import com.Beetles.SystemPayout.backEnd.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/*
Essa anotação faz o SpringBoot reconhecer essa Interface como um repositório
*/

@org.springframework.stereotype.Repository


//Aqui estou extendo o JPA que realiza a conexão com o banco de dados
public interface Repository extends JpaRepository<User, Long> {
}
