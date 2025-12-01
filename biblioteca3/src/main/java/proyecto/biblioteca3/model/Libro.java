package proyecto.biblioteca3.model;

import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;

import java.time.LocalDateTime;

@Document(collection = "libros")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Libro {
    @Id
    private String id; // MongoDB usa String para IDs
    
    @Indexed
    private String titulo;
    
    private String autor;
    
    @Indexed(unique = true)
    private String isbn;
    
    private String descripcion;
    
    private Integer cantidadTotal;
    
    private Integer cantidadDisponible;
    
    private String categoria;
    
    private LocalDateTime fechaIngreso;

    // Hook para establecer valores por defecto
    public void prePersist() {
        if (fechaIngreso == null) {
            fechaIngreso = LocalDateTime.now();
        }
        if (cantidadDisponible == null && cantidadTotal != null) {
            cantidadDisponible = cantidadTotal;
        }
    }
}