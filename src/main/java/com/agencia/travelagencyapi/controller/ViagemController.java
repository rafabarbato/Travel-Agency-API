package com.agencia.travelagencyapi.controller;

import com.agencia.travelagencyapi.model.Avaliacao;
import com.agencia.travelagencyapi.model.Viagem;
import com.agencia.travelagencyapi.service.ViagemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/viagens")
@CrossOrigin(origins = "*")
public class ViagemController {

    @Autowired
    private ViagemService viagemService;

    // GET - Listar todas as viagens (AGORA REFATORADO)
    @GetMapping
    public ResponseEntity<List<Viagem>> listarViagens(
            @RequestParam(required = false) String destino,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) BigDecimal precoMin,
            @RequestParam(required = false) BigDecimal precoMax,
            @RequestParam(required = false, defaultValue = "true") Boolean apenasAtivas) {
        
        // A lógica de decisão foi movida para o ViagemService.
        // O controller apenas repassa os parâmetros.
        List<Viagem> viagens = viagemService.pesquisarViagens(destino, categoria, precoMin, precoMax, apenasAtivas);
        
        return ResponseEntity.ok(viagens);
    }

    // GET - Buscar viagem por ID
    @GetMapping("/{id}")
    public ResponseEntity<Viagem> buscarViagemPorId(@PathVariable Long id) {
        Optional<Viagem> viagem = viagemService.buscarPorId(id);
        
        if (viagem.isPresent()) {
            return ResponseEntity.ok(viagem.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST - Criar nova viagem
    @PostMapping
    public ResponseEntity<?> criarViagem(@Valid @RequestBody Viagem viagem) {
        try {
            Viagem novaViagem = viagemService.criarViagem(viagem);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaViagem);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    // PUT - Atualizar viagem completa
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarViagem(@PathVariable Long id, 
                                           @Valid @RequestBody Viagem viagem) {
        try {
            Optional<Viagem> viagemAtualizada = viagemService.atualizarViagem(id, viagem);
            
            if (viagemAtualizada.isPresent()) {
                return ResponseEntity.ok(viagemAtualizada.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    // PATCH - Atualizar viagem parcialmente
    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizarViagemParcial(@PathVariable Long id, 
                                                   @RequestBody Map<String, Object> campos) {
        try {
            Optional<Viagem> viagemAtualizada = viagemService.atualizarViagemParcial(id, campos);
            
            if (viagemAtualizada.isPresent()) {
                return ResponseEntity.ok(viagemAtualizada.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    // DELETE - Deletar viagem
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarViagem(@PathVariable Long id) {
        boolean deletada = viagemService.deletarViagem(id);
        
        if (deletada) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // PATCH - Desativar viagem (soft delete)
    @PatchMapping("/{id}/desativar")
    public ResponseEntity<?> desativarViagem(@PathVariable Long id) {
        boolean desativada = viagemService.desativarViagem(id);
        
        if (desativada) {
            return ResponseEntity.ok(Map.of("mensagem", "Viagem desativada com sucesso"));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST - Reservar vagas
    @PostMapping("/{id}/reservar")
    public ResponseEntity<?> reservarVagas(@PathVariable Long id, 
                                         @RequestBody Map<String, Integer> request) {
        Integer quantidade = request.get("quantidade");
        
        if (quantidade == null || quantidade <= 0) {
            return ResponseEntity.badRequest()
                    .body(Map.of("erro", "Quantidade deve ser um número positivo"));
        }
        
        boolean reservada = viagemService.reservarVaga(id, quantidade);
        
        if (reservada) {
            return ResponseEntity.ok(Map.of("mensagem", 
                    "Reserva realizada com sucesso para " + quantidade + " vaga(s)"));
        } else {
            return ResponseEntity.badRequest()
                    .body(Map.of("erro", "Não foi possível realizar a reserva. Verifique disponibilidade."));
        }
    }

    // GET - Endpoint de status da API
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> status() {
        Map<String, Object> status = Map.of(
                "status", "API funcionando",
                "versao", "1.0.0",
                "totalViagens", viagemService.listarTodasViagens().size(),
                "viagensAtivas", viagemService.listarViagensAtivas().size()
        );
        return ResponseEntity.ok(status);
    }

    // POST - Criar uma nova avaliação para uma viagem
    @PostMapping("/{id}/avaliacoes")
    public ResponseEntity<?> criarAvaliacao(@PathVariable Long id, @Valid @RequestBody Avaliacao avaliacao) {
        try {
            Avaliacao novaAvaliacao = viagemService.adicionarAvaliacao(id, avaliacao);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaAvaliacao);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    // GET - Listar todas as avaliações de uma viagem
    @GetMapping("/{id}/avaliacoes")
    public ResponseEntity<?> listarAvaliacoes(@PathVariable Long id) {
        try {
            List<Avaliacao> avaliacoes = viagemService.listarAvaliacoesPorViagemId(id);
            return ResponseEntity.ok(avaliacoes);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }
}