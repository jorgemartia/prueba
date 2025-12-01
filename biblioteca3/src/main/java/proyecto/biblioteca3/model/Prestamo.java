package proyecto.biblioteca3.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.index.Indexed;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "prestamos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prestamo {
    @Id
    private String id; // MongoDB usa String para IDs
    
    @DBRef
    @Indexed
    private Usuario usuario;
    
    @DBRef
    @Indexed
    private Libro libro;
    
    private LocalDateTime fechaPrestamo;
    
    private LocalDate fechaDevolucionEsperada;
    
    private LocalDate fechaDevolucionReal;
    
    @Builder.Default
    private EstadoPrestamo estado = EstadoPrestamo.ACTIVO;

    public enum EstadoPrestamo {
        ACTIVO, DEVUELTO, VENCIDO
    }
}