package com.Beetles.systempayout.backend.security.securityService;

import com.Beetles.systempayout.backend.admin.repository.AdminRepository;
import com.Beetles.systempayout.backend.aluno.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final AlunoRepository alunoRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var admin = adminRepository.findByEmail(email);

        var aluno = alunoRepository.findByEmail(email);

        if (admin.isPresent())
            return admin.get();

        if(aluno.isPresent())
            return aluno.get();

        throw new UsernameNotFoundException("Email ou senha incorreta");
    }
}