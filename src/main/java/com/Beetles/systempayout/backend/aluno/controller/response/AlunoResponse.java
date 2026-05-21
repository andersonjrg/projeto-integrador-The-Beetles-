package com.Beetles.systempayout.backend.aluno.controller.response;

import com.Beetles.systempayout.backend.aluno.model.Aluno;
import com.Beetles.systempayout.backend.shared.enums.Enum_Status;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AlunoResponse(String nome,
                            String email,
                            String planoEscolhidoId,
                            Enum_Status status,
                            LocalDateTime diaVencimento) {

    public static AlunoResponse toAlunoResponse(Aluno aluno){
        return new AlunoResponse(
                aluno.getNome(),
                aluno.getEmail(),
                aluno.getPlanoEscolhidoId().getNome(),
                aluno.getStatus(),
                aluno.getDiaVencimento()
        );
    }
}
