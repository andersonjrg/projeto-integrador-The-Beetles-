package com.Beetles.SystemPayout.backEnd.service;

import com.Beetles.SystemPayout.backEnd.entity.User;
import com.Beetles.SystemPayout.backEnd.repository.Repository;

import java.util.List;

//Essa anotação faz o SpringBoot reconhecer essa classe como um service
@org.springframework.stereotype.Service

public class UserService {
    // Isso é uma injeção de dependencia, estou injetando o Repositorio para ser usado nessa classe
    private final Repository repository;

    public UserService(Repository repository) {
        this.repository = repository;
    }

    // Aqui estou criando um metodo para ver todos os usuários do sistema

    public List<User> showUsers() {
        return repository.findAll();
    }

    // Já esse metodo é para ver um usuário pelo Id dele

    public User showUserById(/* Aqui é onde colocamos os parametros do metodo */ Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Id not exist"));
    }

    // Esse é para criar um novo usuário no sistema

    public User saveUser(User user) {
        return repository.save(user);
    }

    // Esse é para deletar um usuário do sistema pelo Id

    public void deleteUserById(Long id) {
        if (!repository.existsById(id)) {
        throw new RuntimeException("Não foi possível deletar o Usuário: Id invalido");
        }
        repository.deleteById(id);
}
    // Esse é para atualizar os dados do usuário

    public User updateUser(Long id, User user){
        User userExist = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Id Not Exist"));
            if(userExist.getName() != null){
            userExist.setName(user.getName());}
            if (userExist.getEmail() != null){
            userExist.setEmail(user.getEmail());}
            return repository.save(userExist);
    }
}
