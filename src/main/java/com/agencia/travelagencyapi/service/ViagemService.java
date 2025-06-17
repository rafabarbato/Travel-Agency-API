package com.agencia.travelagencyapi.service;

import com.agencia.travelagencyapi.model.Viagem;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ViagemService {
    
    private final Map<Long, Viagem> viagensStorage = new HashMap<>();
    private Long proximoId = 1L;

    public ViagemService() {
        // Dados iniciais para teste
        criarViagemInicial("Paris", LocalDate.of(2025, 8, 15), LocalDate.of(2025, 8, 25), 
                         new BigDecimal("2500.00"), "Viagem romântica para Paris", 20, "ECONOMICA");
        criarViagemInicial("Tokyo", LocalDate.of(2025, 9, 10), LocalDate.of(2025, 9, 20), 
                         new BigDecimal("4500.00"), "Aventura cultural no Japão", 15, "EXECUTIVA");
        criarViagemInicial("Nova York", LocalDate.of(2025, 7, 5), LocalDate.of(2025, 7, 12), 
                         new BigDecimal("3200.00"), "A cidade que nunca dorme", 25, "ECONOMICA");
    }

    private void criarViagemInicial(String destino, LocalDate dataPartida, LocalDate dataRetorno, 
                                  BigDecimal preco, String descricao, Integer vagas, String categoria) {
        Viagem viagem = new Viagem(destino, dataPartida, dataRetorno, preco, descricao, vagas, categoria);
        viagem.setId(proximoId++);
        viagensStorage.put(viagem.getId(), viagem);
    }

    public List<Viagem> listarTodasViagens() {
        return new ArrayList<>(viagensStorage.values());
    }

    public List<Viagem> listarViagensAtivas() {
        return viagensStorage.values().stream()
                .filter(Viagem::getAtiva)
                .collect(Collectors.toList());
    }

    public Optional<Viagem> buscarPorId(Long id) {
        return Optional.ofNullable(viagensStorage.get(id));
    }

    public List<Viagem> buscarPorDestino(String destino) {
        return viagensStorage.values().stream()
                .filter(v -> v.getDestino().toLowerCase().contains(destino.toLowerCase()))
                .filter(Viagem::getAtiva)
                .collect(Collectors.toList());
    }

    public List<Viagem> buscarPorFaixaPreco(BigDecimal precoMin, BigDecimal precoMax) {
        return viagensStorage.values().stream()
                .filter(v -> v.getPreco().compareTo(precoMin) >= 0 && 
                           v.getPreco().compareTo(precoMax) <= 0)
                .filter(Viagem::getAtiva)
                .collect(Collectors.toList());
    }

    public List<Viagem> buscarPorCategoria(String categoria) {
        return viagensStorage.values().stream()
                .filter(v -> categoria.equalsIgnoreCase(v.getCategoria()))
                .filter(Viagem::getAtiva)
                .collect(Collectors.toList());
    }

    public Viagem criarViagem(Viagem viagem) {
        validarViagem(viagem);
        viagem.setId(proximoId++);
        viagem.setAtiva(true);
        viagensStorage.put(viagem.getId(), viagem);
        return viagem;
    }

    public Optional<Viagem> atualizarViagem(Long id, Viagem viagemAtualizada) {
        if (!viagensStorage.containsKey(id)) {
            return Optional.empty();
        }
        
        validarViagem(viagemAtualizada);
        viagemAtualizada.setId(id);
        viagensStorage.put(id, viagemAtualizada);
        return Optional.of(viagemAtualizada);
    }

    public Optional<Viagem> atualizarViagemParcial(Long id, Map<String, Object> campos) {
        Optional<Viagem> viagemOpt = buscarPorId(id);
        
        if (viagemOpt.isEmpty()) {
            return Optional.empty();
        }
        
        Viagem viagem = viagemOpt.get();
        
        campos.forEach((campo, valor) -> {
            switch (campo) {
                case "destino":
                    viagem.setDestino((String) valor);
                    break;
                case "dataPartida":
                    viagem.setDataPartida(LocalDate.parse((String) valor));
                    break;
                case "dataRetorno":
                    viagem.setDataRetorno(LocalDate.parse((String) valor));
                    break;
                case "preco":
                    viagem.setPreco(new BigDecimal(valor.toString()));
                    break;
                case "descricao":
                    viagem.setDescricao((String) valor);
                    break;
                case "vagasDisponiveis":
                    viagem.setVagasDisponiveis((Integer) valor);
                    break;
                case "categoria":
                    viagem.setCategoria((String) valor);
                    break;
                case "ativa":
                    viagem.setAtiva((Boolean) valor);
                    break;
            }
        });
        
        return Optional.of(viagem);
    }

    public boolean deletarViagem(Long id) {
        return viagensStorage.remove(id) != null;
    }

    public boolean desativarViagem(Long id) {
        Optional<Viagem> viagemOpt = buscarPorId(id);
        if (viagemOpt.isPresent()) {
            viagemOpt.get().setAtiva(false);
            return true;
        }
        return false;
    }

    public boolean reservarVaga(Long id, Integer quantidade) {
        Optional<Viagem> viagemOpt = buscarPorId(id);
        if (viagemOpt.isPresent()) {
            Viagem viagem = viagemOpt.get();
            if (viagem.getVagasDisponiveis() >= quantidade) {
                viagem.setVagasDisponiveis(viagem.getVagasDisponiveis() - quantidade);
                return true;
            }
        }
        return false;
    }

    private void validarViagem(Viagem viagem) {
        if (viagem.getDataPartida().isAfter(viagem.getDataRetorno())) {
            throw new IllegalArgumentException("Data de partida não pode ser posterior à data de retorno");
        }
        
        if (viagem.getDataPartida().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Data de partida não pode ser no passado");
        }
        
        if (viagem.getVagasDisponiveis() != null && viagem.getVagasDisponiveis() < 0) {
            throw new IllegalArgumentException("Número de vagas não pode ser negativo");
        }
    }
}