package com.Beetles.systempayout.backend.plano.controller.request;

import com.Beetles.systempayout.backend.aluno.model.Aluno;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Set;

public record PlanoRequest(@NotBlank
                           String nome,
                           @Nullable
                           Set<Aluno> alunos,
                           @NotBlank
                           String categoria,
                            @NotNull
                           int frequenciaAulas,
                           @NotNull
                           BigDecimal valor,
                           @NotNull
                           boolean ativo) {
}
