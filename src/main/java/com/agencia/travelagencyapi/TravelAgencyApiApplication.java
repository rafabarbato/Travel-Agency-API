package com.agencia.travelagencyapi;

import com.agencia.travelagencyapi.model.Usuario;
import com.agencia.travelagencyapi.model.Viagem;
import com.agencia.travelagencyapi.repository.UsuarioRepository;
import com.agencia.travelagencyapi.repository.ViagemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootApplication
public class TravelAgencyApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravelAgencyApiApplication.class, args);
    }

    // Bean para inserir dados iniciais ao iniciar a aplicação
    @Bean
    public CommandLineRunner initialData(UsuarioRepository usuarioRepository, ViagemRepository viagemRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Criar usuário administrador
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            // Criptografa a senha antes de salvar
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRole("ADMIN");
            usuarioRepository.save(admin);

            // Criar viagens iniciais
            viagemRepository.save(new Viagem("Paris", LocalDate.of(2025, 8, 15), LocalDate.of(2025, 8, 25),
                    new BigDecimal("2500.00"), "Viagem romântica para Paris", 20, "ECONOMICA"));
            viagemRepository.save(new Viagem("Tokyo", LocalDate.of(2025, 9, 10), LocalDate.of(2025, 9, 20),
                    new BigDecimal("4500.00"), "Aventura cultural no Japão", 15, "EXECUTIVA"));
        };
    }
}