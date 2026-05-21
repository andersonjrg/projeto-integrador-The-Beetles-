package com.Beetles.systempayout.backend.plano.controller.response;

import com.Beetles.systempayout.backend.aluno.model.Aluno;
import com.Beetles.systempayout.backend.plano.model.Plano;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

public record PlanoResponse(String nome,
                            Set<String> alunos,
                            String categoria,
                            BigDecimal valor) {

    public static PlanoResponse toPlanoResponse(Plano plano){
        return new PlanoResponse(
                plano.getNome(),
                plano.getAlunos()
                        .stream()
                        .map(Aluno::getNome)
                        .collect(Collectors.toSet()),
                plano.getCategoria(),
                plano.getValor()
        );
    }
}
