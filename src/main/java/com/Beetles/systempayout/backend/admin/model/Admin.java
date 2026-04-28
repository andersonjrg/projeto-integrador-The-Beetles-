package com.Beetles.systempayout.backend.admin.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "administrador")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "admin_id", updatable = false)
    private UUID adminId;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true, updatable = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;
}
