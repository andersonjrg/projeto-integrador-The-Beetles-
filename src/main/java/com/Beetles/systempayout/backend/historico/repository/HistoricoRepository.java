package com.Beetles.systempayout.backend.historico.repository;

import com.Beetles.systempayout.backend.historico.model.Historico;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HistoricoRepository extends JpaRepository<Historico, UUID> {
    Page<Historico> findAll(int paginas, int itens);
}
