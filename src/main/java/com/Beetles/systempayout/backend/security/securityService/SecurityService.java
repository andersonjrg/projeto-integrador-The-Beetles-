package com.Beetles.systempayout.backend.security.securityService;

import com.Beetles.systempayout.backend.admin.repository.AdminRepository;
import com.Beetles.systempayout.backend.security.securityController.request.AlterarSenhaRequest;
import com.Beetles.systempayout.backend.shared.exception.EmailNotFoundException;
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

    public String alterarSenha(AlterarSenhaRequest request){
        var admin = adminRepository.findByEmail(request.email().toLowerCase())
            .orElseThrow(() -> new EmailNotFoundException("Email não encontrado."));

        admin.setSenha(encoder.encode(request.senha()));
        adminRepository.save(admin);
        return "Senha Alterada com sucesso";
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var admin = adminRepository.findByEmail(email.toLowerCase());

        if (admin.isPresent()) {
            return admin.get();
        }
        throw new UsernameNotFoundException("Email ou senha incorreta");
    }
}