package com.project.antiguaburguers.service;

import com.project.antiguaburguers.dto.LoginResponseDTO;
import com.project.antiguaburguers.dto.LoginUsuarioClienteDTO;
import com.project.antiguaburguers.dto.SignInUsuarioClienteDTO;
import com.project.antiguaburguers.model.Cliente;
import com.project.antiguaburguers.model.UsuarioCliente;
import com.project.antiguaburguers.repository.ClienteRepository;
import com.project.antiguaburguers.repository.UsuarioClienteRepository;
import com.project.antiguaburguers.security.JwtService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioClienteService {


    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final ClienteRepository clienteRepository;
    private final ClienteService clienteService;
    private final UsuarioClienteRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioClienteService(UsuarioClienteRepository usuarioRepository,
                                 AuthenticationManager authManager,
                                 JwtService jwtService, PasswordEncoder passwordEncoder, ClienteRepository clienteRepository) {
        this.usuarioRepository = usuarioRepository;
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.clienteRepository = clienteRepository;
    }

    public String register(SignInUsuarioClienteDTO dto) {
        // validar que no exista usuario
        if (usuarioRepository.existsById(dto.usuario())) {
            throw new IllegalArgumentException("El usuario ya existe");
        }

        UsuarioCliente entity = new UsuarioCliente();
        entity.setUsuario(dto.usuario());
        entity.setDpi(dto.dpi());
        entity.setPasswordHash(passwordEncoder.encode(dto.password()));

        usuarioRepository.save(entity);
        return jwtService.generateToken(dto.usuario(), "CLIENTE" );
    }

    @Transactional
    public String registerClient(){
        Cliente
    }

    public LoginResponseDTO logIn(LoginUsuarioClienteDTO dto) {
        var auth = new UsernamePasswordAuthenticationToken(dto.usuario(), dto.password());
        authManager.authenticate(auth); // si falla -> exception

        UsuarioCliente usuario = usuarioRepository.findById(dto.usuario()).orElseThrow(
                () -> new EntityNotFoundException(
                        "Usuario no encontrado con id: " + dto.usuario())
        );
        Cliente cliente = clienteRepository.findById(usuario.getDpi()).orElseThrow(
                () -> new IllegalArgumentException("El cliente no existe")
        );

        Boolean isAdmin = Boolean.TRUE.equals(usuario.getIsAdmin());

        String token = jwtService.generateToken(dto.usuario(), (isAdmin) ? "ADMIN" : "CLIENTE");
        String refreshToken = jwtService.generateRefreshToken(dto.usuario(), isAdmin ? "ADMIN" : "CLIENTE");
        return new LoginResponseDTO(token, dto.usuario(), cliente.getDpi(), isAdmin);
    }
}
