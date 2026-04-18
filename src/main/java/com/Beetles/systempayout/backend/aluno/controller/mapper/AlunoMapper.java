package com.Beetles.systempayout.backend.aluno.controller.mapper;

import com.Beetles.systempayout.backend.aluno.controller.request.AlunoRequest;
import com.Beetles.systempayout.backend.aluno.controller.response.AlunoResponse;
import com.Beetles.systempayout.backend.aluno.model.Aluno;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AlunoMapper{
    public static Aluno mapRequest(AlunoRequest request){

        return Aluno
                .builder()
                .email(request.email())
                .senha(request.senha())
                .nome(request.nome())
                .planoEscolhidoId(request.planoEscolhidoId())
                .build();
    }
    public static AlunoResponse mapResponse(Aluno aluno){
        return AlunoResponse
                .builder()
                .nome(aluno.getNome())
                .email(aluno.getEmail())
                .planoEscolhidoId(aluno.getPlanoEscolhidoId())
                .build();
    }
}
