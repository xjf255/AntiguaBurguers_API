package com.project.antiguaburguers.repository;

import com.project.antiguaburguers.model.UsuarioCliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioClienteRepository extends JpaRepository<UsuarioCliente, String> {
    boolean existsByUsuario(String usuario);
}
