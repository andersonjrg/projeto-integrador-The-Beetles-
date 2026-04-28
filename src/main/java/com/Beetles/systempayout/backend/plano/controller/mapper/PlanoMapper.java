package com.Beetles.systempayout.backend.plano.controller.mapper;


import com.Beetles.systempayout.backend.aluno.controller.mapper.AlunoMapper;
import com.Beetles.systempayout.backend.plano.controller.request.PlanoRequest;
import com.Beetles.systempayout.backend.plano.controller.response.PlanoResponse;
import com.Beetles.systempayout.backend.plano.model.Plano;
import lombok.experimental.UtilityClass;



@UtilityClass
public class PlanoMapper {
    public static Plano mapRequest(PlanoRequest request){

        return Plano
                .builder()
                .nome(request.nome())
                .categoria(request.categoria())
                .frequenciaAulas(request.frequenciaAulas())
                .valor(request.valor())
                .ativo(request.ativo())
                .alunos(request.alunos())
                .build();
    }

    public static PlanoResponse mapResponse(Plano plano){

        return PlanoResponse
                .builder()
                .nome(plano.getNome())
                .alunos(plano.getAlunos())
                .categoria(plano.getCategoria())
                .frequenciaAulas(plano.getFrequenciaAulas())
                .valor(plano.getValor())
                .build();
    }
}
