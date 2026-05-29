package com.Beetles.systempayout.backend.security.securityController;


import com.Beetles.systempayout.backend.admin.controller.request.AdminRequest;
import com.Beetles.systempayout.backend.admin.controller.response.AdminResponse;
import com.Beetles.systempayout.backend.admin.service.AdminService;
import com.Beetles.systempayout.backend.security.securityController.request.AlterarSenhaRequest;
import com.Beetles.systempayout.backend.security.securityController.request.LoginRequest;
import com.Beetles.systempayout.backend.security.securityController.response.LoginResponse;
import com.Beetles.systempayout.backend.security.securityService.SecurityService;
import com.Beetles.systempayout.backend.security.tokenService.TokenService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
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

import java.util.Map;


@RestController
@RequestMapping("/auth")
@RateLimiter(name = "securityRateLimiter", fallbackMethod = "rateLimiterResponse")
public class SecurityController {
    private final AdminService adminService;
    private final SecurityService securityService;
    private final ObjectProvider<AuthenticationManager> authenticationManager;
    private final TokenService tokenService;

    public SecurityController(AdminService adminService, SecurityService securityService, ObjectProvider<AuthenticationManager> authenticationManager, TokenService tokenService) {
        this.adminService = adminService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.securityService = securityService;
    }

    @PostMapping("/admin/register")
    public ResponseEntity<AdminResponse> registrarAdmin(@RequestBody @Valid AdminRequest request){
        var admin = adminService.registrar(request);
        var response = AdminResponse.toAdminResponse(admin);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/admin/alterarsenha")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>>  alterarSenha(@RequestBody @Valid AlterarSenhaRequest request) {
        securityService.alterarSenha(request);
        return ResponseEntity.ok(Map.of("message", "Senha alterada com sucesso"));
    }
    

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request){
        var userAndPass = new UsernamePasswordAuthenticationToken(request.email().toLowerCase(), request.senha());
        var auth = authenticationManager.getObject().authenticate(userAndPass);
        var userDetails = (UserDetails) auth.getPrincipal();
        var token = tokenService.generateToken(userDetails);
        return ResponseEntity.ok(new LoginResponse(token));
    }

    public ResponseEntity<String> rateLimiterResponse(){
        return ResponseEntity.ok("Muitas requisições, espere um momento e tente novamente");
    }
}
