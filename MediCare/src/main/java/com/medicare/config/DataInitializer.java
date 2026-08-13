package com.medicare.config;

import com.medicare.domain.Rol;
import com.medicare.domain.Usuario;
import com.medicare.repository.RolRepository;
import com.medicare.repository.UsuarioRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements ApplicationRunner {

    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RolRepository rolRepository, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.rolRepository = rolRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Insert roles if missing
        if (rolRepository.count() == 0) {
            rolRepository.saveAll(List.of(new Rol("ADMIN"), new Rol("MEDICO"), new Rol("PACIENTE")));
        }

        // Insert users if missing
        if (usuarioRepository.count() == 0) {
            Rol admin = rolRepository.findByNombre("ADMIN").orElse(null);
            Rol medico = rolRepository.findByNombre("MEDICO").orElse(null);
            Rol paciente = rolRepository.findByNombre("PACIENTE").orElse(null);

            Usuario u1 = new Usuario();
            u1.setNombre("Admin General");
            u1.setEmail("admin@medicare.com");
            u1.setPassword(passwordEncoder.encode("12345"));
            u1.setRol(admin);
            usuarioRepository.save(u1);

            Usuario u2 = new Usuario();
            u2.setNombre("Dr. Roberto");
            u2.setEmail("medico@medicare.com");
            u2.setPassword(passwordEncoder.encode("12345"));
            u2.setRol(medico);
            usuarioRepository.save(u2);

            Usuario u3 = new Usuario();
            u3.setNombre("Paciente Maria");
            u3.setEmail("paciente@medicare.com");
            u3.setPassword(passwordEncoder.encode("12345"));
            u3.setRol(paciente);
            usuarioRepository.save(u3);
        } else {
            // Ensure passwords are encoded: naive migration (encode if not starting with $2a$)
            usuarioRepository.findAll().forEach(u -> {
                String p = u.getPassword();
                if (p != null && !p.startsWith("$2a$") && !p.startsWith("$2b$") && !p.startsWith("$2y$")) {
                    u.setPassword(passwordEncoder.encode(p));
                    usuarioRepository.save(u);
                }
            });
        }
    }
}
