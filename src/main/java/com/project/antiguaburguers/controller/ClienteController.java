package com.project.antiguaburguers.controller;

import com.project.antiguaburguers.dto.ClienteDTO;
import com.project.antiguaburguers.service.ClienteService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*") // opcional, útil para frontend en otro dominio
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    /* ==============================
       CONSULTAS / LECTURA
       ============================== */

    // Buscar cliente por DPI
    @GetMapping("/{dpi}")
    public ResponseEntity<ClienteDTO> obtenerPorDpi(@PathVariable String dpi) {
        try {
            var dto = clienteService.buscarPorDpi(dpi);
            return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // (Opcional) listar todos los clientes
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listarTodos() {
        var clientes = clienteService.obtenerTodos(); // te lo agrego abajo en el service
        return ResponseEntity.ok(clientes);
    }

    /* ==============================
       CREAR / ACTUALIZAR
       ============================== */

    // Crear nuevo cliente
    @PostMapping
    public ResponseEntity<?> crearCliente(@RequestBody ClienteDTO dto) {
        try {
            var creado = clienteService.crearCliente(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }

    // Actualizar cliente existente
    @PutMapping("/{dpi}")
    public ResponseEntity<?> actualizarCliente(@PathVariable String dpi, @RequestBody ClienteDTO dto) {
        try {
            var actualizado = clienteService.actualizarCliente(dpi, dto);
            return ResponseEntity.ok(actualizado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /* ==============================
       ELIMINAR (opcional)
       ============================== */

    @DeleteMapping("/{dpi}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable String dpi) {
        try {
            clienteService.eliminarCliente(dpi);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
