package com.Beetles.systempayout.backend.admin.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AdminRequest(@NotBlank
                           String nome,
                           @NotBlank
                           String email,
                           @Size(min = 8, max = 15, message = "A senha deve conter entre 8 e 15 caracteres")
                           String senha) {
}
