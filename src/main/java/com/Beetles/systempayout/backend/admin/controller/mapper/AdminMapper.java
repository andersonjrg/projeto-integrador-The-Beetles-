package com.Beetles.systempayout.backend.admin.controller.mapper;

import com.Beetles.systempayout.backend.admin.controller.request.AdminRequest;
import com.Beetles.systempayout.backend.admin.controller.response.AdminResponse;
import com.Beetles.systempayout.backend.admin.model.Admin;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AdminMapper {

    public static Admin requestMapper(AdminRequest request){
        return Admin
                .builder()
                .email(request.email())
                .senha(request.senha())
                .nome(request.nome())
                .build();
    }

    public static AdminResponse responseMapper(Admin admin){
        return AdminResponse
                .builder()
                .nome(admin.getNome())
                .email(admin.getEmail())
                .build();
    }
}
