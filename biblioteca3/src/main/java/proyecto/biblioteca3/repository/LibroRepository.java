package proyecto.biblioteca3.repository;

import proyecto.biblioteca3.model.Libro;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LibroRepository extends MongoRepository<Libro, String> {
    List<Libro> findByTituloContainingIgnoreCase(String titulo);
    List<Libro> findByAutorContainingIgnoreCase(String autor);
    List<Libro> findByCantidadDisponibleGreaterThan(Integer cantidad);
}
