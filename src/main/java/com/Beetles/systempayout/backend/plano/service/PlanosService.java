package com.Beetles.systempayout.backend.plano.service;

import com.Beetles.systempayout.backend.plano.model.Plano;
import com.Beetles.systempayout.backend.plano.repository.PlanoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
public class PlanosService {
    private final PlanoRepository repository;

    public PlanosService(PlanoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Plano criarPlano(Plano plano){
        plano.onCreated();
        return repository.save(plano);
    }

    public List<Plano> mostrarTodosPlanos(int paginas, int itens){
        Pageable pageable = PageRequest.of(paginas, itens);
        Page<Plano> planoPage = repository.findAll(pageable);
        return planoPage.getContent();
    }

    public Plano mostrarPlanoEspecificoPeloId(UUID id){
        return repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Id não encontrado"));
    }

    @Transactional
    public Plano modificarPlano(Plano plano, UUID id){
        Plano planoAnterior = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Id não encontrado"));
        if (plano.getNome() != null){
            planoAnterior.setNome(plano.getNome());
        }
        if (plano.getCategoria() != null){
            planoAnterior.setCategoria(plano.getCategoria());
        }
        if (plano.getValor() != null){
            planoAnterior.setValor(plano.getValor());
        }
        if (plano.getAlunos() != null){
            planoAnterior.setAlunos(plano.getAlunos());
        }
        if(plano.getFrequenciaAulas() != 0) {
            planoAnterior.setFrequenciaAulas(plano.getFrequenciaAulas());
        }
        return repository.save(planoAnterior);
    }

    @Transactional
    public void deletarPlano(UUID id){
        if(!repository.existsById(id)){
            throw new RuntimeException("O Id não existe");
        }
            repository.deleteById(id);
    }
}