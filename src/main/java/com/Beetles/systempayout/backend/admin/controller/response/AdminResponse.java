package com.Beetles.systempayout.backend.admin.controller.response;

import lombok.Builder;

@Builder
public record AdminResponse(String nome,
                            String email) {
}
