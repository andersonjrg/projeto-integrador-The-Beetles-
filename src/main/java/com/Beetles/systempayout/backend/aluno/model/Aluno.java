package com.Beetles.systempayout.backend.aluno.model;

import com.Beetles.systempayout.backend.plano.model.Plano;
import com.Beetles.systempayout.backend.shared.enums.Enum_Status;
import com.Beetles.systempayout.backend.shared.enums.Enums_roles;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="alunos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "planoEscolhidoId")
public class Aluno implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "aluno_id",nullable = false, unique = true)
    private UUID alunoId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String numero;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, length = 20)
    private String senha;

    @ManyToOne
    @JoinColumn(name = "plano_escolhido_id", nullable = true)
    private Plano planoEscolhidoId;

    private boolean primeiroAcesso;

    private Enum_Status status;

    private LocalDateTime diaVencimento;

    @Column(name = "role")
    private Enums_roles role = Enums_roles.ALUNOS;

    @CreationTimestamp
    private LocalDateTime dataInicioPlano;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime dataCadastro;

    @PrePersist
    public void roles(){
        if(this.role == null){
            this.role = Enums_roles.ALUNOS;
        }
    }

    public void calcularVencimento(){
        if(this.dataInicioPlano == null){
            throw new RuntimeException("O usuário não possui um plano cadastrado");
        }else{
            diaVencimento = dataInicioPlano.plusMonths(1);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getRole()));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
