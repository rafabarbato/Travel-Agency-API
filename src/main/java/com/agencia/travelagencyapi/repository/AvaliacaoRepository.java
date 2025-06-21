package com.agencia.travelagencyapi.repository;

import com.agencia.travelagencyapi.model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    
    // Método para buscar todas as avaliações de uma viagem específica
    List<Avaliacao> findByViagemId(Long viagemId);
}