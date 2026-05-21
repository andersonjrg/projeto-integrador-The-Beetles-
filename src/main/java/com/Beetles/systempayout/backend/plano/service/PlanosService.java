package com.Beetles.systempayout.backend.plano.service;

import com.Beetles.systempayout.backend.plano.controller.request.PlanoRequest;
import com.Beetles.systempayout.backend.plano.model.Plano;
import com.Beetles.systempayout.backend.plano.repository.PlanoRepository;
import com.Beetles.systempayout.backend.shared.exception.IdNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
public class PlanosService {
    private final PlanoRepository repository;

    public PlanosService(PlanoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Plano criarPlano(PlanoRequest request){
        Plano plano = new Plano();

        plano.setNome(request.nome());
        plano.setCategoria(request.categoria());
        plano.setValor(request.valor());
        plano.setAtivo(request.ativo());
        plano.setAlunos(request.alunos());

        return repository.save(plano);
    }

    public Page<Plano> mostrarTodosPlanos(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Plano mostrarPlanoEspecificoPeloId(UUID id){
        return repository.findById(id)
                .orElseThrow(()-> new IdNotFoundException(id));
    }

    @Transactional
    public Plano modificarPlano(PlanoRequest request, UUID id){
        Plano plano = repository.findById(id)
                .orElseThrow(()-> new IdNotFoundException(id));

        if (request.nome() != null){
            plano.setNome(request.nome());
        }
        if (request.categoria() != null){
            plano.setCategoria(request.categoria());
        }
        if (request.valor() != null){
            plano.setValor(request.valor());
        }
        if (request.alunos() != null){
            plano.setAlunos(request.alunos());
        }
        return repository.save(plano);
    }

    @Transactional
    public void deletarPlano(UUID id){
        if(!repository.existsById(id)){
            throw new IdNotFoundException(id);
        }
            repository.deleteById(id);
    }
}