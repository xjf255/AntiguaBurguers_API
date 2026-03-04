package com.project.antiguaburguers.controller;

import com.project.antiguaburguers.dto.*;
import com.project.antiguaburguers.service.UsuarioClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UsuarioClienteService usuarioClienteService;

    public AuthController(UsuarioClienteService usuarioClienteService) {
        this.usuarioClienteService = usuarioClienteService;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponseDTO> signIn(@RequestBody SignInUsuarioClienteDTO dto) {
        String userId = usuarioClienteService.register(dto);
        return ResponseEntity.ok(new AuthResponseDTO(true, userId, "Usuario creado"));
    }

    @PostMapping("/log-in")
    public ResponseEntity<LoginResponseDTO> logIn(@RequestBody LoginUsuarioClienteDTO dto) {
        return ResponseEntity.ok(usuarioClienteService.logIn(dto));
    }

}
