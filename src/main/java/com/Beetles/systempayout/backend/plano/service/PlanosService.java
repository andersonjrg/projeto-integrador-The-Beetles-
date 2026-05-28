package com.Beetles.systempayout.backend.plano.service;

import com.Beetles.systempayout.backend.aluno.model.Aluno;
import com.Beetles.systempayout.backend.aluno.repository.AlunoRepository;
import com.Beetles.systempayout.backend.plano.controller.request.PlanoRequest;
import com.Beetles.systempayout.backend.plano.controller.response.PlanoResponse;
import com.Beetles.systempayout.backend.plano.model.Plano;
import com.Beetles.systempayout.backend.plano.repository.PlanoRepository;
import com.Beetles.systempayout.backend.shared.exception.IdNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class PlanosService {
    private final PlanoRepository repository;
    private final AlunoRepository alunoRepository;

    public PlanosService(PlanoRepository repository, AlunoRepository alunoRepository) {
        this.repository = repository;
        this.alunoRepository = alunoRepository;
    }

    @Transactional
    public Plano criarPlano(PlanoRequest request){

        List<Aluno> aluno = new ArrayList<>();
        if (request.alunos() != null && !request.alunos().isEmpty()) {
            aluno = alunoRepository.findAllById(request.alunos());
        }

        Plano plano = new Plano();

        plano.setNome(request.nome());
        plano.setCategoria(request.categoria());
        plano.setValor(request.valor());
        plano.setAtivo(request.ativo());
        plano.setAlunos(aluno);
        plano.setFrequenciaAulas(request.frequenciaAulas());
        return repository.save(plano);
    }

    @Transactional(readOnly = true)
    public Page<PlanoResponse> mostrarTodosPlanos(Pageable pageable){
        return repository.findAll(pageable)
                .map(PlanoResponse::toPlanoResponse);
    }

    @Transactional(readOnly = true)
    public PlanoResponse mostrarPlanoEspecificoPeloId(UUID id){
        return repository.findById(id)
                .map(PlanoResponse::toPlanoResponse)
                .orElseThrow(()-> new IdNotFoundException("Plano não encontrado."));
    }

    @Transactional
    public Plano modificarPlano(PlanoRequest request, UUID id){
        Plano plano = repository.findById(id)
                .orElseThrow(()-> new IdNotFoundException("Plano não encontrado."));

        List<Aluno> aluno = new ArrayList<>();
        if (request.alunos() != null && !request.alunos().isEmpty()) {
            aluno = alunoRepository.findAllById(request.alunos());
        };

        if (request.nome() != null){
            plano.setNome(request.nome());
        }
        if (request.categoria() != null){
            plano.setCategoria(request.categoria());
        }
        if (request.valor() != null){
            plano.setValor(request.valor());
        }
        plano.setFrequenciaAulas(request.frequenciaAulas());
        plano.setAtivo(request.ativo());
        plano.setAlunos(aluno);
        return repository.save(plano);
    }

    @Transactional
    public void deletarPlano(UUID id){
        if(!repository.existsById(id)){
            throw new IdNotFoundException("Plano não encontrado.");
        }
        List<Aluno> alunos = alunoRepository.findByPlanoEscolhidoIdPlanoId(id);
        for (Aluno aluno : alunos) {
            aluno.setPlanoEscolhidoId(null);
            alunoRepository.save(aluno);
        }
            repository.deleteById(id);
    }
}