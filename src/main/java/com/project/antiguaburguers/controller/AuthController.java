package com.project.antiguaburguers.controller;

import com.project.antiguaburguers.dto.*;
import com.project.antiguaburguers.service.UsuarioClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioClienteService usuarioClienteService;

    public AuthController(UsuarioClienteService usuarioClienteService) {
        this.usuarioClienteService = usuarioClienteService;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<LoginResponseDTO> signIn(@RequestBody CreateRegisterDTO dto) {
        return usuarioClienteService.registerClient(dto);
    }

    @PostMapping("/log-in")
    public ResponseEntity<LoginResponseDTO> logIn(@RequestBody LoginUsuarioClienteDTO dto) {
        return usuarioClienteService.logIn(dto);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@CookieValue(value = "access_token", required = false) String accessToken, @CookieValue(value = "refresh_token", required = false) String refreshToken) {
        if (accessToken == null || refreshToken == null) {
            return ResponseEntity.ok("You are not logged in.");
        }
        return usuarioClienteService.logOut();
    }

    @GetMapping("/home")
    public String home(@CookieValue(value = "access_token", required = false) String accessToken, @CookieValue(value = "refresh_token", required = false) String refreshToken) {
        return (accessToken == null || refreshToken == null) ? "Please login first" : "Welcome to the Home Page!";
    }

}
