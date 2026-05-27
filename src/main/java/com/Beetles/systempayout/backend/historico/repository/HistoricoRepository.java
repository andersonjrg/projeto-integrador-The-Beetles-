package com.Beetles.systempayout.backend.historico.repository;

import com.Beetles.systempayout.backend.historico.model.Historico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HistoricoRepository extends JpaRepository<Historico, UUID> {
    List<Historico> findByAlunoAlunoId(UUID id);
    void deleteByAlunoAlunoId(UUID alunoId);
}
