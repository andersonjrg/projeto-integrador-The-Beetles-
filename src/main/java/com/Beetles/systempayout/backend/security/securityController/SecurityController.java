package com.Beetles.systempayout.backend.security.securityController;


import com.Beetles.systempayout.backend.admin.controller.request.AdminRequest;
import com.Beetles.systempayout.backend.admin.controller.response.AdminResponse;
import com.Beetles.systempayout.backend.admin.service.AdminService;
import com.Beetles.systempayout.backend.aluno.controller.request.AlunoRequest;
import com.Beetles.systempayout.backend.aluno.controller.response.AlunoResponse;
import com.Beetles.systempayout.backend.aluno.service.AlunoService;
import com.Beetles.systempayout.backend.security.securityController.request.LoginRequest;
import com.Beetles.systempayout.backend.security.securityController.response.LoginResponse;
import com.Beetles.systempayout.backend.security.tokenService.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("auth")
public class SecurityController {
    private final AlunoService alunoService;
    private final AdminService adminService;
    private final ObjectProvider<AuthenticationManager> authenticationManager;
    private final TokenService tokenService;

    public SecurityController(AlunoService alunoService, AdminService adminService, ObjectProvider<AuthenticationManager> authenticationManager, TokenService tokenService) {
        this.alunoService = alunoService;
        this.adminService = adminService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/aluno/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AlunoResponse> registrar(@RequestBody @Valid AlunoRequest request){
        var aluno = alunoService.registerUser(request);
        var response = AlunoResponse.toAlunoResponse(aluno);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/admin/register")
    public ResponseEntity<AdminResponse> registrar(@RequestBody @Valid AdminRequest request){
        var admin = adminService.registrar(request);
        var response = AdminResponse.toAdminResponse(admin);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request){
        var userAndPass = new UsernamePasswordAuthenticationToken(request.email(), request.senha());
        var auth = authenticationManager.getObject().authenticate(userAndPass);
        var userDetails = (UserDetails) auth.getPrincipal();
        var token = tokenService.generateToken(userDetails);
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
