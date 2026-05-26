package com.Beetles.systempayout.backend.aluno.controller.request;

import com.Beetles.systempayout.backend.shared.enums.Enum_Status;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;


public record AlunoRequest(@NotBlank
                           String nome,
                           @Nullable
                           UUID plano,
                           @NotNull
                           Enum_Status status,
                           @Nullable
                           LocalDateTime dataInicioPlano
                           ) {
}
