package com.Beetles.systempayout.backend.admin.controller.response;


import com.Beetles.systempayout.backend.admin.model.Admin;

public record AdminResponse(String nome,
                            String email) {

    public static AdminResponse toAdminResponse(Admin admin){
        return new AdminResponse(
                admin.getNome(),
                admin.getEmail()
        );
    }
}
