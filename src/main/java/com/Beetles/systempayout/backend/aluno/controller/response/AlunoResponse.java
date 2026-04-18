package com.Beetles.systempayout.backend.aluno.controller.response;

import com.Beetles.systempayout.backend.plano.model.Plano;
import lombok.Builder;

@Builder
public record AlunoResponse(String nome,
                            String email,
                            Plano planoEscolhidoId) {
}
