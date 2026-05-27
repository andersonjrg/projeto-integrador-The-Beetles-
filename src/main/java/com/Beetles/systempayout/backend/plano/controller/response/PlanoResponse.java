package com.Beetles.systempayout.backend.plano.controller.response;

import com.Beetles.systempayout.backend.aluno.model.Aluno;
import com.Beetles.systempayout.backend.plano.model.Plano;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record PlanoResponse(UUID planoId,
                            String nome,
                            Set<String> alunos,
                            String categoria,
                            int frequenciaAulas,
                            boolean ativo,
                            BigDecimal valor) {

    public static PlanoResponse toPlanoResponse(Plano plano){

        Set<String> alunoPlano = null;

        if (plano.getAlunos() != null) {
            alunoPlano = plano.getAlunos()
                    .stream()
                    .map(Aluno::getNome)
                    .collect(Collectors.toSet());
        }

        return new PlanoResponse(
                plano.getPlanoId(),
                plano.getNome(),
                alunoPlano,
                plano.getCategoria(),
                plano.getFrequenciaAulas(),
                plano.isAtivo(),
                plano.getValor()
        );
    }
}
