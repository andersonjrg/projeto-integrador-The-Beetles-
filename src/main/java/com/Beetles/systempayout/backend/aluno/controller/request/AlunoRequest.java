package com.Beetles.systempayout.backend.aluno.controller.request;


import com.Beetles.systempayout.backend.plano.model.Plano;
import lombok.Builder;

@Builder
public record AlunoRequest(String email,
                           String nome,
                           String senha,
                           Plano planoEscolhidoId) {
}
