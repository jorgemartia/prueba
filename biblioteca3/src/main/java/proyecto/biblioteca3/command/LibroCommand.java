package proyecto.biblioteca3.command;

import proyecto.biblioteca3.model.Libro;
import proyecto.biblioteca3.service.LibroService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class LibroCommand implements Command<Libro> {

    private final LibroService libroService;
    private Libro libro;
    private TipoOperacion operacion;

    public enum TipoOperacion {
        AGREGAR, ACTUALIZAR
    }

    public LibroCommand configurar(Libro libro, TipoOperacion operacion) {
        this.libro = libro;
        this.operacion = operacion;
        return this;
    }

    @Override
    public Libro ejecutar() {
        if (libro == null || operacion == null) {
            throw new IllegalStateException("Debe configurar el libro y la operación");
        }

        return switch (operacion) {
            case AGREGAR -> agregarLibro();
            case ACTUALIZAR -> actualizarLibro();
        };
    }

    private Libro agregarLibro() {
        validarCampos();

        if (libro.getId() == null || libro.getId().isEmpty()) {
            libro.setCantidadDisponible(libro.getCantidadTotal());
            libro.setFechaIngreso(LocalDateTime.now());
        }

        return libroService.guardar(libro);
    }

    private Libro actualizarLibro() {
        validarCampos();
        return libroService.guardar(libro);
    }

    private void validarCampos() {
        if (libro.getTitulo() == null || libro.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("El título del libro es requerido");
        }
        if (libro.getAutor() == null || libro.getAutor().trim().isEmpty()) {
            throw new IllegalArgumentException("El autor del libro es requerido");
        }
        if (libro.getCantidadTotal() == null || libro.getCantidadTotal() <= 0) {
            throw new IllegalArgumentException("La cantidad total debe ser mayor a 0");
        }
    }
}