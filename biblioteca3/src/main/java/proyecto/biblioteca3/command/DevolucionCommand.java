package proyecto.biblioteca3.command;

import proyecto.biblioteca3.model.Prestamo;
import proyecto.biblioteca3.service.LibroService;
import proyecto.biblioteca3.service.PrestamoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DevolucionCommand implements Command<Prestamo> {

    private final PrestamoService prestamoService;
    private final LibroService libroService;
    private String prestamoId; // Cambio de Integer a String

    public DevolucionCommand configurar(String prestamoId) {
        this.prestamoId = prestamoId;
        return this;
    }

    @Override
    public Prestamo ejecutar() {
        if (prestamoId == null) {
            throw new IllegalStateException("Debe configurar el ID del préstamo");
        }

        Prestamo prestamo = prestamoService.obtenerPorId(prestamoId);

        if (prestamo == null) {
            throw new IllegalArgumentException("Préstamo no encontrado");
        }

        if (prestamo.getEstado() == Prestamo.EstadoPrestamo.DEVUELTO) {
            throw new IllegalStateException("Este préstamo ya fue devuelto");
        }

        prestamo.setFechaDevolucionReal(LocalDate.now());
        prestamo.setEstado(Prestamo.EstadoPrestamo.DEVUELTO);

        libroService.devolverLibro(prestamo.getLibro().getId());

        return prestamoService.actualizar(prestamo);
    }

    public Prestamo ejecutar(String prestamoId) {
        this.prestamoId = prestamoId;
        return ejecutar();
    }
}