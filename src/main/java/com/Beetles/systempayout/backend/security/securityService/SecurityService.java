package com.Beetles.systempayout.backend.security.securityService;

import com.Beetles.systempayout.backend.admin.repository.AdminRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class SecurityService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder encoder;

    public SecurityService(AdminRepository adminRepository, PasswordEncoder encoder) {
        this.adminRepository = adminRepository;
        this.encoder = encoder;
    }

    public String alterarSenha(String email, String senha){
        var admin = adminRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("email não encontrado"));
            
        admin.setSenha(encoder.encode(senha));
        return "Senha Alterada com sucesso";
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var admin = adminRepository.findByEmail(email);

        if (admin.isPresent()) {
            return admin.get();
        }
        throw new UsernameNotFoundException("Email ou senha incorreta");
    }
}