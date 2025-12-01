package proyecto.biblioteca3.service;

import proyecto.biblioteca3.model.*;
import proyecto.biblioteca3.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LibroService {
    private final LibroRepository libroRepository;
    
    public Libro guardar(Libro libro) {
        try {
            // Validaciones básicas
            if (libro.getTitulo() == null || libro.getTitulo().trim().isEmpty()) {
                throw new RuntimeException("El título del libro es requerido");
            }
            if (libro.getAutor() == null || libro.getAutor().trim().isEmpty()) {
                throw new RuntimeException("El autor del libro es requerido");
            }
            if (libro.getCantidadTotal() == null || libro.getCantidadTotal() <= 0) {
                throw new RuntimeException("La cantidad total debe ser mayor a 0");
            }
            
            // Si es nuevo libro, establecer valores por defecto
            if (libro.getId() == null || libro.getId().isEmpty()) {
                libro.setCantidadDisponible(libro.getCantidadTotal());
                libro.setFechaIngreso(LocalDateTime.now());
            }
            
            libro.prePersist();
            return libroRepository.save(libro);
        } catch (Exception e) {
            System.err.println("Error en LibroService.guardar: " + e.getMessage());
            throw new RuntimeException("Error al guardar el libro: " + e.getMessage());
        }
    }
    
    public List<Libro> obtenerTodos() {
        return libroRepository.findAll();
    }
    
    public Optional<Libro> obtenerPorId(String id) {
        return libroRepository.findById(id);
    }
    
    public void eliminar(String id) {
        libroRepository.deleteById(id);
    }
    
    public boolean prestarLibro(String libroId) {
        Optional<Libro> libroOpt = libroRepository.findById(libroId);
        if (libroOpt.isPresent()) {
            Libro libro = libroOpt.get();
            if (libro.getCantidadDisponible() > 0) {
                libro.setCantidadDisponible(libro.getCantidadDisponible() - 1);
                libroRepository.save(libro);
                return true;
            }
        }
        return false;
    }
    
    public void devolverLibro(String libroId) {
        Optional<Libro> libroOpt = libroRepository.findById(libroId);
        if (libroOpt.isPresent()) {
            Libro libro = libroOpt.get();
            if (libro.getCantidadDisponible() < libro.getCantidadTotal()) {
                libro.setCantidadDisponible(libro.getCantidadDisponible() + 1);
                libroRepository.save(libro);
            }
        }
    }
}