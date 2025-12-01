// LibroController.java
package proyecto.biblioteca3.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import proyecto.biblioteca3.dto.*;
import proyecto.biblioteca3.model.*;
import proyecto.biblioteca3.service.*;
import proyecto.biblioteca3.command.*;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/libros")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LibroController {

    private final LibroService libroService;
    private final UsuarioService usuarioService;
    private final ProxyService proxyService;
    private final LibroCommand libroCommand;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Libro>>> listar() {
        return ResponseEntity.ok(ApiResponse.<List<Libro>>builder()
                .exito(true)
                .datos(libroService.obtenerTodos())
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Libro>> obtener(@PathVariable String id) {
        return libroService.obtenerPorId(id)
                .map(l -> ResponseEntity.ok(ApiResponse.<Libro>builder()
                        .exito(true)
                        .datos(l)
                        .build()))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Libro>> crear(
            @RequestBody Libro libro,
            @RequestParam String usuarioId) {
        try {
            Usuario usuario = usuarioService.obtenerPorId(usuarioId)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            if (!proxyService.puedeGestionarLibros(usuario)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(ApiResponse.<Libro>builder()
                                .exito(false)
                                .mensaje("No tiene permisos para registrar libros")
                                .build());
            }

            Libro creado = libroCommand
                    .configurar(libro, LibroCommand.TipoOperacion.AGREGAR)
                    .ejecutar();

            return ResponseEntity.ok(ApiResponse.<Libro>builder()
                    .exito(true)
                    .mensaje("Libro registrado exitosamente")
                    .datos(creado)
                    .build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.<Libro>builder()
                            .exito(false)
                            .mensaje(e.getMessage())
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<Libro>builder()
                            .exito(false)
                            .mensaje("Error al registrar libro: " + e.getMessage())
                            .build());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Libro>> actualizar(
            @PathVariable String id,
            @RequestBody Libro libro,
            @RequestParam String usuarioId) {
        try {
            Usuario usuario = usuarioService.obtenerPorId(usuarioId)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            if (!proxyService.puedeGestionarLibros(usuario)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(ApiResponse.<Libro>builder()
                                .exito(false)
                                .mensaje("No tiene permisos para actualizar libros")
                                .build());
            }

            libro.setId(id);

            Libro actualizado = libroCommand
                    .configurar(libro, LibroCommand.TipoOperacion.ACTUALIZAR)
                    .ejecutar();

            return ResponseEntity.ok(ApiResponse.<Libro>builder()
                    .exito(true)
                    .mensaje("Libro actualizado exitosamente")
                    .datos(actualizado)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<Libro>builder()
                            .exito(false)
                            .mensaje(e.getMessage())
                            .build());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(
            @PathVariable String id,
            @RequestParam String usuarioId) {
        try {
            Usuario usuario = usuarioService.obtenerPorId(usuarioId)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            if (!proxyService.puedeGestionarLibros(usuario)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(ApiResponse.<Void>builder()
                                .exito(false)
                                .mensaje("No tiene permisos para eliminar libros")
                                .build());
            }

            libroService.eliminar(id);
            return ResponseEntity.ok(ApiResponse.<Void>builder()
                    .exito(true)
                    .mensaje("Libro eliminado exitosamente")
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<Void>builder()
                            .exito(false)
                            .mensaje(e.getMessage())
                            .build());
        }
    }
}