package proyecto.biblioteca3.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import lombok.*;

import java.time.LocalDateTime;

@Document(collection = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {
    @Id
    private String id; // MongoDB usa String para IDs
    
    private String nombre;
    
    private String apellido;
    
    @Indexed(unique = true)
    private String cedula;
    
    private String telefono;
    
    @Indexed(unique = true)
    private String email;
    
    private String clave;
    
    @Builder.Default
    private RolUsuario rol = RolUsuario.USUARIO;
    
    private LocalDateTime fechaRegistro;
    
    @Builder.Default
    private Boolean activo = true;

    // Hook para establecer valores por defecto
    public void prePersist() {
        if (fechaRegistro == null) {
            fechaRegistro = LocalDateTime.now();
        }
        if (activo == null) {
            activo = true;
        }
        if (rol == null) {
            rol = RolUsuario.USUARIO;
        }
    }

    public enum RolUsuario {
        USUARIO, ADMIN
    }
}