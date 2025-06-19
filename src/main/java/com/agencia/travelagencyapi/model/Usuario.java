package com.agencia.travelagencyapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Usuario {
    @Id
    private String username;
    private String password;
    private String role; // Ex: "ADMIN", "USER"

    // Construtor vazio
    public Usuario() {
    }

    // Getters e Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}