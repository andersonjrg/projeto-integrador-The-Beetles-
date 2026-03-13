package com.Beetles.SystemPayout.backEnd.service;

import com.Beetles.SystemPayout.backEnd.entity.User;
import com.Beetles.SystemPayout.backEnd.exceptions.DeleteUserError;
import com.Beetles.SystemPayout.backEnd.exceptions.SaveUserException;
import com.Beetles.SystemPayout.backEnd.exceptions.ShowUserError;
import com.Beetles.SystemPayout.backEnd.exceptions.UpdateUserError;
import com.Beetles.SystemPayout.backEnd.repository.Repository;

import java.util.List;

//Essa anotação faz o SpringBoot reconhecer essa classe como um service
@org.springframework.stereotype.Service

public class Service {
    // Isso é uma injeção de dependencia, estou injetando o Repositorio para ser usado nessa classe
    Repository repository;
    public Service(Repository repository) {
        this.repository = repository;
    }

    // Aqui estou criando um metodo para ver todos os usuários do sistema
    public List<User> showUsers(){
        // O uso do try, é para tratamento de exceções
        try {
            return repository.findAll();
        } catch (ShowUserError e) {
            throw new ShowUserError(e.getMessage());
        }
    }

    // Já esse metodo é para ver um usuário pelo Id dele
    public User showUserById(/* Aqui é onde colocamos os parametros do metodo */ Long id){
        try{
            return repository.findById(id)
                    .orElseThrow(()-> new RuntimeException("Id not exist"));
        } catch (ShowUserError e) {
            throw new ShowUserError(e.getMessage());
        }
    }

    // Esse é para criar um novo usuário no sistema
    public User saveUser(User user){
        try {
            return repository.save(user);
        } catch (SaveUserException e) {
            throw new SaveUserException(e.getMessage());
        }
    }

    // Esse é para deletar um usuário do sistema pelo Id
    public void deleteUserById(Long id){
        try{repository.deleteById(id);
    } catch (DeleteUserError e) {
            throw new DeleteUserError(e.getMessage());
        }
    }

    // Esse é para atualizar os dados do usuário
    public User updateUser(Long id, User user){
        User userExist = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Id Not Exist"));
        try {
            user.setName(userExist.getName());
            user.setEmail(userExist.getEmail());
            user.setPassword(userExist.getPassword());
            return repository.save(user);
        } catch (UpdateUserError e) {
            throw new UpdateUserError(e.getMessage());
        }
    }
}
