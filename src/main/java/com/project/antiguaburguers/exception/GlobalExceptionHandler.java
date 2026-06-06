package com.project.antiguaburguers.exception;
import com.project.antiguaburguers.dto.ErrorResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class) // indica qué excepción captura este método
    public ResponseEntity<ErrorResponseDTO> handleEntityNotFound(
            EntityNotFoundException ex,      // la excepción capturada con su mensaje original
            HttpServletRequest request        // objeto de la petición HTTP para leer la ruta /api/...
    ) {
        // Construimos el cuerpo del error con todos los campos del DTO
        ErrorResponseDTO body = new ErrorResponseDTO(
                LocalDateTime.now(),             // timestamp: ahora mismo
                HttpStatus.NOT_FOUND.value(),    // status: 404
                "Not Found",                     // error: nombre legible del código HTTP
                ex.getMessage(),                 // message: el texto pasado al EntityNotFoundException
                request.getRequestURI()          // path: la URL exacta que falló, ej: "/api/pedidos/PED001"
        );

        // Devolvemos la respuesta con el status 404 y el JSON del error
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // HANDLER 2: IllegalArgumentException
    // Se lanza cuando los datos de entrada son inválidos para la lógica de negocio.
    // Ej: "Cantidad inválida en ítem", "El usuario ya existe"
    // Respuesta esperada: HTTP 400 Bad Request
    // ─────────────────────────────────────────────────────────────────────────
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgument(
            IllegalArgumentException ex,
            HttpServletRequest request
    ) {
        ErrorResponseDTO body = new ErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),  // status: 400
                "Bad Request",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // HANDLER 3: IllegalStateException
    // Se lanza cuando el estado actual del sistema no permite la operación solicitada.
    // Ej: "El repartidor no está disponible", "Este pedido no requiere entrega"
    // Respuesta esperada: HTTP 409 Conflict (el recurso existe pero su estado lo impide)
    // ─────────────────────────────────────────────────────────────────────────
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalState(
            IllegalStateException ex,
            HttpServletRequest request
    ) {
        ErrorResponseDTO body = new ErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),     // status: 409
                "Conflict",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // HANDLER 4: Exception (comodín)
    // Captura cualquier excepción inesperada que no haya sido manejada arriba.
    // Actúa como red de seguridad para evitar que Spring devuelva un error 500
    // con stack trace en texto plano visible al frontend.
    // Respuesta esperada: HTTP 500 Internal Server Error
    // ─────────────────────────────────────────────────────────────────────────
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(
            Exception ex,
            HttpServletRequest request
    ) {
        ErrorResponseDTO body = new ErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(), // status: 500
                "Internal Server Error",
                // Usamos un mensaje genérico para no exponer detalles internos al cliente
                "Ocurrió un error inesperado. Contacte al administrador.",
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
