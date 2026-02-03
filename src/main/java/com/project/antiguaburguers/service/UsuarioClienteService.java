package com.project.antiguaburguers.service;

import com.project.antiguaburguers.dto.LoginResponseDTO;
import com.project.antiguaburguers.dto.LoginUsuarioClienteDTO;
import com.project.antiguaburguers.dto.SignInUsuarioClienteDTO;
import com.project.antiguaburguers.model.UsuarioCliente;
import com.project.antiguaburguers.repository.UsuarioClienteRepository;
import com.project.antiguaburguers.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioClienteService {


    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UsuarioClienteRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioClienteService(UsuarioClienteRepository usuarioRepository,
                                 AuthenticationManager authManager,
                                 JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public String signIn(SignInUsuarioClienteDTO dto) {
        // validar que no exista usuario
        if (usuarioRepository.existsById(dto.usuario())) {
            throw new IllegalArgumentException("El usuario ya existe");
        }

        UsuarioCliente entity = new UsuarioCliente();
        entity.setUsuario(dto.usuario());
        entity.setDpi(dto.dpi());
        entity.setPasswordHash(passwordEncoder.encode(dto.password()));

        usuarioRepository.save(entity);
        return entity.getUsuario(); // o retorna un UUID si tuvieras uno
    }

    public LoginResponseDTO logIn(LoginUsuarioClienteDTO dto) {
        var auth = new UsernamePasswordAuthenticationToken(dto.usuario(), dto.password());
        authManager.authenticate(auth); // si falla -> exception

        String token = jwtService.generateToken(dto.usuario());
        return new LoginResponseDTO(token, dto.usuario());
    }
}
