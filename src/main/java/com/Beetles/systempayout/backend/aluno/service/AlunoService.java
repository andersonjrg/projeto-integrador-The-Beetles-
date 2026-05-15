package com.Beetles.systempayout.backend.aluno.service;

import com.Beetles.systempayout.backend.aluno.model.Aluno;
import com.Beetles.systempayout.backend.aluno.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AlunoService{

    private final AlunoRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<Aluno> listUsers(int paginas, int itens) {
        Pageable pageable = PageRequest.of(paginas, itens);
        Page<Aluno> alunoPage = repository.findAll(pageable);
        return alunoPage.getContent();
    }


    @Transactional(readOnly = true)
    public Aluno listUserById(UUID id) {
        return repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Id não encontrado"));
    }

    @Transactional
    public Aluno registerUser(Aluno aluno) {
        String senha = aluno.getSenha();
        aluno.setSenha(passwordEncoder.encode(senha));
        return repository.save(aluno);
    }


    @Transactional
    public void deleteUserById(UUID id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Não foi possível deletar o Usuário: Id invalido");
        }
        repository.deleteById(id);
    }

    @Transactional
    public Aluno updateUser(UUID id, Aluno aluno) {
        Aluno alunoExist = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Não foi possível atualizar o Usuário: Id inválido"));

        if (aluno.getNome() != null) {
            alunoExist.setNome(aluno.getNome());
        }
        if (aluno.getNumero() != null){
            alunoExist.setNumero(aluno.getNumero());
        }
        if (aluno.getEmail() != null) {
            alunoExist.setEmail(aluno.getEmail());
        }
        if (aluno.getSenha() != null){
            String senha = aluno.getSenha();
            alunoExist.setSenha(passwordEncoder.encode(senha));
        }
        if (aluno.getPlanoEscolhidoId() != null) {
            alunoExist.setPlanoEscolhidoId(aluno.getPlanoEscolhidoId());
        }
        return repository.save(alunoExist);

    }
}