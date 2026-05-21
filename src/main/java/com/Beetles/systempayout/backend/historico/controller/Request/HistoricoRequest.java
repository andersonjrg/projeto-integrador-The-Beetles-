package com.Beetles.systempayout.backend.historico.controller.Request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record HistoricoRequest(@NotNull
                               UUID alunoId,
                               BigDecimal valor) {
}
