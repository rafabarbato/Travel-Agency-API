package com.agencia.travelagencyapi.service;

import com.agencia.travelagencyapi.model.Viagem;
import com.agencia.travelagencyapi.repository.ViagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ViagemService {

    @Autowired
    private ViagemRepository viagemRepository;

    // NOVO MÉTODO QUE CENTRALIZA A LÓGICA DE BUSCA
    public List<Viagem> pesquisarViagens(String destino, String categoria, BigDecimal precoMin, BigDecimal precoMax, Boolean apenasAtivas) {
        if (destino != null && !destino.isEmpty()) {
            return viagemRepository.findByDestinoContainingIgnoreCaseAndAtiva(destino, apenasAtivas);
        } else if (categoria != null && !categoria.isEmpty()) {
            return viagemRepository.findByCategoriaIgnoreCaseAndAtiva(categoria, apenasAtivas);
        } else if (precoMin != null && precoMax != null) {
            return viagemRepository.findByPrecoBetweenAndAtiva(precoMin, precoMax, apenasAtivas);
        } else if (apenasAtivas) {
            return viagemRepository.findByAtiva(true);
        } else {
            return viagemRepository.findAll();
        }
    }

    public List<Viagem> listarTodasViagens() {
        return viagemRepository.findAll();
    }

    public List<Viagem> listarViagensAtivas() {
        return viagemRepository.findByAtiva(true);
    }

    public Optional<Viagem> buscarPorId(Long id) {
        return viagemRepository.findById(id);
    }

    public List<Viagem> buscarPorDestino(String destino) {
        return viagemRepository.findByDestinoContainingIgnoreCaseAndAtiva(destino, true);
    }

    public List<Viagem> buscarPorFaixaPreco(BigDecimal precoMin, BigDecimal precoMax) {
        return viagemRepository.findByPrecoBetweenAndAtiva(precoMin, precoMax, true);
    }

    public List<Viagem> buscarPorCategoria(String categoria) {
        return viagemRepository.findByCategoriaIgnoreCaseAndAtiva(categoria, true);
    }
    
    // ... resto da classe permanece igual (criarViagem, atualizarViagem, etc.)
    
    @Transactional
    public Viagem criarViagem(Viagem viagem) {
        validarViagem(viagem);
        viagem.setAtiva(true);
        return viagemRepository.save(viagem);
    }

    @Transactional
    public Optional<Viagem> atualizarViagem(Long id, Viagem viagemAtualizada) {
        return viagemRepository.findById(id).map(viagemExistente -> {
            validarViagem(viagemAtualizada);
            viagemAtualizada.setId(id);
            return viagemRepository.save(viagemAtualizada);
        });
    }

    @Transactional
    public Optional<Viagem> atualizarViagemParcial(Long id, Map<String, Object> campos) {
        Optional<Viagem> viagemOpt = viagemRepository.findById(id);

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

        validarViagem(viagem);
        return Optional.of(viagemRepository.save(viagem));
    }

    @Transactional
    public boolean deletarViagem(Long id) {
        if (viagemRepository.existsById(id)) {
            viagemRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean desativarViagem(Long id) {
        Optional<Viagem> viagemOpt = viagemRepository.findById(id);
        if (viagemOpt.isPresent()) {
            Viagem viagem = viagemOpt.get();
            viagem.setAtiva(false);
            viagemRepository.save(viagem);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean reservarVaga(Long id, Integer quantidade) {
        Optional<Viagem> viagemOpt = viagemRepository.findById(id);
        if (viagemOpt.isPresent()) {
            Viagem viagem = viagemOpt.get();
            if (viagem.getAtiva() && viagem.getVagasDisponiveis() >= quantidade) {
                viagem.setVagasDisponiveis(viagem.getVagasDisponiveis() - quantidade);
                viagemRepository.save(viagem);
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