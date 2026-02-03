package com.project.antiguaburguers.security;

import com.project.antiguaburguers.model.UsuarioCliente;
import com.project.antiguaburguers.repository.UsuarioClienteRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioClienteRepository repo;

    public CustomUserDetailsService(UsuarioClienteRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioCliente u = repo.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return User.builder()
                .username(u.getUsuario())
                .password(u.getPasswordHash())
                .roles("CLIENTE") // si no tienes roles aún, fijo
                .build();
    }
}
