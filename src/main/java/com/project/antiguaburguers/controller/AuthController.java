package com.project.antiguaburguers.controller;

import com.project.antiguaburguers.dto.*;
import com.project.antiguaburguers.service.UsuarioClienteService;
import jakarta.servlet.http.HttpServletResponse;
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
    public ResponseEntity<LoginResponseDTO> signIn(@RequestBody CreateRegisterDTO dto, HttpServletResponse response) {
        return ResponseEntity.ok(usuarioClienteService.registerClient(dto, response));
    }

    @PostMapping("/log-in")
    public ResponseEntity<LoginResponseDTO> logIn(@RequestBody LoginUsuarioClienteDTO dto, @CookieValue(value = "token", required = false) String token, HttpServletResponse response) {
        if(token != null) return null;
        return ResponseEntity.ok(usuarioClienteService.logIn(dto,response));
    }

    @GetMapping("/logout")
    public String logout(@CookieValue(value = "access_token", required = false) String token, HttpServletResponse response) {
        if (token == null) {
            return "You are not logged in.";
        }
        return usuarioClienteService.logOut(response);
    }

    @GetMapping("/home")
    public String home(@CookieValue(value = "access_token", required = false) String token, @CookieValue(value = "refresh_token", required = false) String refreshToken) {
        return (token == null) ? "Please login first" : "Welcome to the Home Page!";
    }

}
