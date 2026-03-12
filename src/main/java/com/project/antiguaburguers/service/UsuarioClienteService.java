package com.project.antiguaburguers.service;

import com.project.antiguaburguers.dto.*;
import com.project.antiguaburguers.model.UsuarioCliente;
import com.project.antiguaburguers.repository.UsuarioClienteRepository;
import com.project.antiguaburguers.security.JwtService;
import com.project.antiguaburguers.utils.RolEnum;
import com.project.antiguaburguers.utils.TokenEnum;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioClienteService {
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final ClienteService clienteService;
    private final UsuarioClienteRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final long expirationMinutes;
    private final long expirationRefreshMinutes;
    private final String profile;

    public UsuarioClienteService(UsuarioClienteRepository usuarioRepository,
                                 AuthenticationManager authManager,
                                 @Value("${security.jwt.expiration-minutes}") long expirationMinutes,
                                 @Value("${security.jwt.expiration-refresh-minutes}") long expirationRefreshMinutes,
                                 @Value("${spring.profiles.active : dev}") String profile,
                                 JwtService jwtService, PasswordEncoder passwordEncoder, ClienteService clienteService) {
        this.usuarioRepository = usuarioRepository;
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.clienteService = clienteService;
        this.expirationMinutes = expirationMinutes;
        this.expirationRefreshMinutes = expirationRefreshMinutes;
        this.profile = profile;
    }

    public UsuarioCliente register(SignInUsuarioClienteDTO dto) {
        // validar que no exista usuario
        if (usuarioRepository.existsById(dto.usuario())) {
            throw new IllegalArgumentException("El usuario ya existe");
        }

        UsuarioCliente entity = new UsuarioCliente();
        entity.setUsuario(dto.usuario());
        entity.setDpi(dto.dpi());
        entity.setPasswordHash(passwordEncoder.encode(dto.password()));

        usuarioRepository.save(entity);
        return entity;
    }

    @Transactional
    public ResponseEntity<LoginResponseDTO> registerClient(CreateRegisterDTO dto){
        ClienteDTO client = clienteService.crearCliente(dto.client());
        UsuarioCliente register = register(dto.signInUsuarioClienteDTO());
        String token = jwtService.generateToken(register.getUsuario(), RolEnum.CLIENTE);
        String refreshToken = jwtService.generateRefreshToken(register.getUsuario(), RolEnum.CLIENTE);

        ResponseCookie cookie = createCookie(TokenEnum.ACCESS_TOKEN, token, expirationMinutes);
        ResponseCookie refreshCookie = createCookie(TokenEnum.REFRESH_TOKEN, refreshToken, expirationRefreshMinutes);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString()).body(
                new LoginResponseDTO(register.getUsuario(), client.dpi(), register.getIsAdmin())
        );
    }

    public ResponseEntity<LoginResponseDTO> logIn(LoginUsuarioClienteDTO dto) {
        var auth = new UsernamePasswordAuthenticationToken(dto.usuario(), dto.password());
        authManager.authenticate(auth); // si falla -> exception

        UsuarioCliente usuario = usuarioRepository.findById(dto.usuario()).orElseThrow(
                () -> new EntityNotFoundException(
                        "Usuario no encontrado con id: " + dto.usuario())
        );
        ClienteDTO cliente = clienteService.buscarPorDpi(usuario.getDpi());
        Boolean isAdmin = Boolean.TRUE.equals(usuario.getIsAdmin());

        String token = jwtService.generateToken(dto.usuario(), (isAdmin) ? RolEnum.ADMINISTRADOR : RolEnum.CLIENTE);
        String refreshToken = jwtService.generateRefreshToken(dto.usuario(), isAdmin ? RolEnum.ADMINISTRADOR : RolEnum.CLIENTE);

        ResponseCookie cookie = createCookie(TokenEnum.ACCESS_TOKEN, token, expirationMinutes);
        ResponseCookie refreshCookie = createCookie(TokenEnum.REFRESH_TOKEN, refreshToken, expirationRefreshMinutes);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(new LoginResponseDTO(dto.usuario(), cliente.dpi(), isAdmin));
    }

    public ResponseEntity<String> logOut() {
        ResponseCookie cookie = createCookie(TokenEnum.ACCESS_TOKEN, "", 0);
        ResponseCookie refreshCookie = createCookie(TokenEnum.REFRESH_TOKEN, "", 0);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body("Logout successful!");
    }

    private ResponseCookie createCookie(TokenEnum tokenName, String token, long minAge) {
        boolean isSecure = !profile.equalsIgnoreCase("dev");
        return ResponseCookie.from(tokenName.toString(), token)
                .httpOnly(true)
                .secure(isSecure) // Should be true in production with HTTPS
                .path("/")
                .maxAge(minAge * 60)
                .sameSite("None")
                .build();
    }
}
