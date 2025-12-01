package proyecto.biblioteca3.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import proyecto.biblioteca3.model.*;
import proyecto.biblioteca3.repository.*;

import java.time.LocalDateTime;

/**
 * Inicializador de datos de prueba para MongoDB
 * Crea usuarios admin y regular, y algunos libros de ejemplo
 */
@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(
            UsuarioRepository usuarioRepo,
            LibroRepository libroRepo,
            PasswordEncoder passwordEncoder) {
        
        return args -> {
            if (usuarioRepo.count() > 0) {
                System.out.println("✓ Base de datos ya inicializada");
                return;
            }

            System.out.println("🔄 Inicializando datos de prueba en MongoDB...");

            Usuario admin = Usuario.builder()
                    .nombre("Administrador")
                    .apellido("Sistema")
                    .cedula("1234567890")
                    .telefono("3001234567")
                    .email("admin@biblioteca.com")
                    .clave(passwordEncoder.encode("admin123"))
                    .rol(Usuario.RolUsuario.ADMIN)
                    .activo(true)
                    .fechaRegistro(LocalDateTime.now())
                    .build();
            admin.prePersist();
            usuarioRepo.save(admin);
            System.out.println("✓ Usuario admin creado");

           
    };
}
}