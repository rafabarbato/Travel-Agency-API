package com.agencia.travelagencyapi.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import java.math.BigDecimal;

public class Viagem {
    
    private Long id;
    
    @NotBlank(message = "Destino é obrigatório")
    private String destino;
    
    @NotNull(message = "Data de partida é obrigatória")
    private LocalDate dataPartida;
    
    @NotNull(message = "Data de retorno é obrigatória")
    private LocalDate dataRetorno;
    
    @NotNull(message = "Preço é obrigatório")
    @Positive(message = "Preço deve ser positivo")
    private BigDecimal preco;
    
    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;
    
    private Integer vagasDisponiveis;
    
    private String categoria; // ECONOMICA, EXECUTIVA, PRIMEIRA_CLASSE
    
    private Boolean ativa;

    // Construtores
    public Viagem() {
        this.ativa = true;
        this.vagasDisponiveis = 0;
    }

    public Viagem(String destino, LocalDate dataPartida, LocalDate dataRetorno, 
                  BigDecimal preco, String descricao, Integer vagasDisponiveis, String categoria) {
        this();
        this.destino = destino;
        this.dataPartida = dataPartida;
        this.dataRetorno = dataRetorno;
        this.preco = preco;
        this.descricao = descricao;
        this.vagasDisponiveis = vagasDisponiveis;
        this.categoria = categoria;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public LocalDate getDataPartida() {
        return dataPartida;
    }

    public void setDataPartida(LocalDate dataPartida) {
        this.dataPartida = dataPartida;
    }

    public LocalDate getDataRetorno() {
        return dataRetorno;
    }

    public void setDataRetorno(LocalDate dataRetorno) {
        this.dataRetorno = dataRetorno;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getVagasDisponiveis() {
        return vagasDisponiveis;
    }

    public void setVagasDisponiveis(Integer vagasDisponiveis) {
        this.vagasDisponiveis = vagasDisponiveis;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Boolean getAtiva() {
        return ativa;
    }

    public void setAtiva(Boolean ativa) {
        this.ativa = ativa;
    }

    @Override
    public String toString() {
        return "Viagem{" +
                "id=" + id +
                ", destino='" + destino + '\'' +
                ", dataPartida=" + dataPartida +
                ", dataRetorno=" + dataRetorno +
                ", preco=" + preco +
                ", vagasDisponiveis=" + vagasDisponiveis +
                ", categoria='" + categoria + '\'' +
                ", ativa=" + ativa +
                '}';
    }
}