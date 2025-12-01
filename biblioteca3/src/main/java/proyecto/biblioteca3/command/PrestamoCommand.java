package proyecto.biblioteca3.command;

import proyecto.biblioteca3.model.*;
import proyecto.biblioteca3.service.PrestamoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PrestamoCommand implements Command<Prestamo> {

    private final PrestamoService prestamoService;
    private Prestamo prestamo;

    public PrestamoCommand conPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
        return this;
    }

    @Override
    public Prestamo ejecutar() {
        if (prestamo == null) {
            throw new IllegalStateException("Debe configurar el préstamo antes de ejecutar");
        }

        prestamo.setFechaPrestamo(LocalDateTime.now());
        prestamo.setFechaDevolucionEsperada(LocalDate.now().plusDays(14));
        prestamo.setEstado(Prestamo.EstadoPrestamo.ACTIVO);

        return prestamoService.guardar(prestamo);
    }

    public Prestamo ejecutar(Prestamo prestamo) {
        this.prestamo = prestamo;
        return ejecutar();
    }
}