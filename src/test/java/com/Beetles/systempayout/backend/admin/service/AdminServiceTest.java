package com.Beetles.systempayout.backend.admin.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import com.Beetles.systempayout.backend.admin.controller.request.AdminRequest;
import com.Beetles.systempayout.backend.admin.model.Admin;
import com.Beetles.systempayout.backend.admin.repository.AdminRepository;
import com.Beetles.systempayout.backend.shared.enums.Enums_roles;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {
    
    @Mock
    AdminRepository adminRepository;

    @Mock
    PasswordEncoder passwordEncoder;
    
    @InjectMocks
    AdminService adminService;

    @Test
    void Registrar_admin_quando_a_solicitacao_e_um_sucesso() {
        AdminRequest request = new AdminRequest(
            "matheus", 
            "matheuscr909@gmail.com", 
            "matheus");

        Admin admin = new Admin(
            request.nome(),
            request.email(),
            request.senha(),
            Enums_roles.ADMIN);

        when(passwordEncoder.encode(request.senha())).thenReturn("matheus");
        when(adminRepository.save(any(Admin.class))).thenReturn(admin);

        Admin result = adminService.registrar(request);

        assertNotNull(result);
        assertEquals(admin.getAdminId(), result.getAdminId());
        assertEquals("matheus", result.getNome());
        assertEquals("matheuscr909@gmail.com", result.getEmail());
        assertEquals("matheus", result.getSenha());
        assertEquals(Enums_roles.ADMIN, result.getRole());
    }
    

    @Test
    void testDeletarAdmin() {

    }

    @Test
    void Buscar_por_email_quando_for_um_sucesso() {
        AdminRequest request = new AdminRequest(
            "matheus",
            "matheuscr909@gmail.com",
            "matheus"
        );
        Admin admin = new Admin(
            request.nome(),
            request.email(),
            request.senha(),
            Enums_roles.ADMIN
        );

        when(adminRepository.save(any(Admin.class))).thenReturn(admin);

        var buscar = adminService.buscarPorEmail(admin.getEmail());

        assertNotNull(admin);
        assertNotNull(buscar);
        assertEquals(admin.getEmail(), buscar.email());
        assertEquals(admin.getSenha(), buscar.email());
    }
}
