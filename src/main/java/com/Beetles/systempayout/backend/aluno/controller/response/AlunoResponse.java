package com.Beetles.systempayout.backend.aluno.controller.response;

import com.Beetles.systempayout.backend.aluno.model.Aluno;
import com.Beetles.systempayout.backend.shared.enums.Enum_Status;

import java.time.LocalDateTime;
import java.util.UUID;

public record AlunoResponse(UUID id,
                            String nome,
                            UUID planoId,
                            String planoNome,
                            Enum_Status status,
                            LocalDateTime diaVencimento) {

    public static AlunoResponse toAlunoResponse(Aluno aluno){

        String planoNome = null;
        UUID planoId = null;

        if (aluno.getPlanoEscolhidoId() != null) {
            planoNome = aluno.getPlanoEscolhidoId().getNome();
            planoId = aluno.getPlanoEscolhidoId().getPlanoId();
        }
        return new AlunoResponse(
                aluno.getAlunoId(),
                aluno.getNome(),
                planoId,
                planoNome,
                aluno.getStatus(),
                aluno.getDiaVencimento()
        );
    }
}
