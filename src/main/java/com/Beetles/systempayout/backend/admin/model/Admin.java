package com.Beetles.systempayout.backend.admin.model;

import com.Beetles.systempayout.backend.shared.enums.Enums_roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "administrador")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Admin implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "admin_id", updatable = false)
    private UUID adminId;

    @Column(name = "role")
    private Enums_roles role = null;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true, updatable = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    @PrePersist
    public void roles(){
        if(this.role == null){
            this.role = Enums_roles.ADMIN;
        }
    }

    @Override
    public String toString() {
        return "role: " + role + "\n"+
                ", nome: " + nome + "\n"+
                ", email: " + email + "\n";
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
