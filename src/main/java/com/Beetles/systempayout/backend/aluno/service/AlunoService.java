package com.Beetles.systempayout.backend.aluno.service;

import com.Beetles.systempayout.backend.aluno.DTO.AlunoDTO;
import com.Beetles.systempayout.backend.aluno.Mapper.AlunoMapper;
import com.Beetles.systempayout.backend.aluno.model.Aluno;
import com.Beetles.systempayout.backend.aluno.repository.AlunoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

//Essa anotação faz o SpringBoot reconhecer a Classe como um componente de serviço, ou seja, é onde fica a lógica de negócio da aplicação, aqui é onde ficam os metodos que fazem as operações com o banco de dados, usando o Repositorio e o Mapper para converter os objetos de Aluno para AlunoDTO e vice versa
@org.springframework.stereotype.Service

public class AlunoService {
    // Isso é uma injeção de dependencia, estou injetando o Repositorio para usar os metodos de acesso ao banco de dados, e o Mapper para converter os objetos de Aluno para AlunoDTO e vice versa
    //Toda classe que for injetada em outra classe tem que ser anotada com @Component ou alguma anotação que seja um estereotipo do SpringBoot, como @Service, @Repository, @Controller, etc
    private final AlunoRepository repository;
    private final AlunoMapper alunoMapper;

    public AlunoService(AlunoRepository repository, AlunoMapper alunoMapper) {
        this.repository = repository;
        this.alunoMapper = alunoMapper;
    }

    // Aqui estou usando a anotação @Transactional para indicar que esse metodo é uma transação, ou seja, é uma operação que envolve o acesso ao banco de dados, e o readOnly = true indica que esse metodo é apenas para leitura, ou seja, não vai modificar os dados do banco de dados, isso pode ajudar a otimizar a performance do banco de dados, pois ele pode usar cache para esse tipo de operação

    @Transactional(readOnly = true)
    public List<AlunoDTO> showUsers() {
        List<Aluno> alunos = repository.findAll();
        return alunos.stream()
                .map(alunoMapper::map)
                .collect(Collectors.toList());
    }

    // Já esse metodo é para ver um usuário pelo Id dele
    @Transactional(readOnly = true)
    public AlunoDTO showUserById(/* Aqui é onde colocamos os parametros do metodo */ UUID id) {
        Optional<Aluno> alunoID = repository.findById(id);
        return alunoID.map(alunoMapper::map).orElse(null);
    }

    // Esse é para criar um novo usuário no sistema

    @Transactional
    public AlunoDTO saveUser(AlunoDTO alunoDTO) {
        Aluno aluno = alunoMapper.map(alunoDTO);
        aluno.setPrimeiroAcesso(true);
        aluno.transformarTelefone();
        aluno = repository.save(aluno);
        return alunoMapper.map(aluno);
    }

    // Esse é para deletar um usuário do sistema pelo Id

    @Transactional
    public void deleteUserById(UUID id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Não foi possível deletar o Usuário: Id invalido");
        }
        repository.deleteById(id);
    }
    // Esse é para atualizar os dados do usuário

    @Transactional
    public AlunoDTO updateUser(UUID id, AlunoDTO alunoDTO) {
        Aluno alunoExist = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Não foi possível atualizar o Usuário: Id inválido"));

        // Atualizar apenas os campos que não são nulos
        if (alunoDTO.getNome() != null) {
            alunoExist.setNome(alunoDTO.getNome());
        }
        if (alunoDTO.getTelefone() != null) {
            alunoExist.setTelefone(alunoDTO.getTelefone());
            alunoExist.transformarTelefone();
        }
        if (alunoDTO.getStatus() != null) {
            alunoExist.setStatus(alunoDTO.getStatus());
        }
        if (alunoDTO.getPlanoEscolhidoId() != null) {
            alunoExist.setPlanoEscolhidoId(alunoDTO.getPlanoEscolhidoId());
        }
        Aluno alunoAtualizado = repository.save(alunoExist);
        return alunoMapper.map(alunoAtualizado);
    }
}