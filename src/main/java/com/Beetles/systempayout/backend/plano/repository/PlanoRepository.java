package com.Beetles.systempayout.backend.plano.repository;

import com.Beetles.systempayout.backend.plano.model.Plano;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlanoRepository extends JpaRepository<Plano, UUID> {
    Page<Plano> findAll(int paginas, int itens);
}
