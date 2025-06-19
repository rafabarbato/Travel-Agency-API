package com.agencia.travelagencyapi.repository;

import com.agencia.travelagencyapi.model.Viagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ViagemRepository extends JpaRepository<Viagem, Long> {

    // Método para buscar viagens ativas
    List<Viagem> findByAtiva(boolean ativa);

    // Métodos para busca com filtro e apenas ativas
    List<Viagem> findByDestinoContainingIgnoreCaseAndAtiva(String destino, boolean ativa);
    
    List<Viagem> findByCategoriaIgnoreCaseAndAtiva(String categoria, boolean ativa);
    
    List<Viagem> findByPrecoBetweenAndAtiva(BigDecimal precoMin, BigDecimal precoMax, boolean ativa);
}