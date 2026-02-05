package com.project.antiguaburguers.config;

import com.project.antiguaburguers.model.Cliente;
import com.project.antiguaburguers.model.UsuarioCliente;
import com.project.antiguaburguers.repository.ClienteRepository;
import com.project.antiguaburguers.repository.UsuarioClienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UsuarioAdminRunner implements CommandLineRunner {
    private final UsuarioClienteRepository usuarioClienteRepository;
    private final ClienteRepository clienteRepository;
    private final AdminProperties data;
    private final PasswordEncoder passwordEncoder;

    public UsuarioAdminRunner(UsuarioClienteRepository usuarioClienteRepository, ClienteRepository clienteRepository, AdminProperties data, PasswordEncoder passwordEncoder) {
        this.usuarioClienteRepository = usuarioClienteRepository;
        this.clienteRepository = clienteRepository;
        this.data = data;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        boolean existsAdmin = usuarioClienteRepository.existsByUsuario(data.getUsername());
        log.info("Ejecutando UsuarioAdminRunner...");
        log.info("Admin existente");
        if (!existsAdmin) {
            log.info("Admin no existente");
            Cliente usuarioAdmin = new Cliente();
            usuarioAdmin.setNombre(data.getNombre());
            usuarioAdmin.setApellido(data.getApellido());
            usuarioAdmin.setEmail(data.getEmail());
            usuarioAdmin.setPrefijo_telefono(data.getPrefijoTelefono());
            usuarioAdmin.setTelefono(data.getTelefono());
            usuarioAdmin.setDpi(data.getDpi());
            usuarioAdmin.setDireccion(data.getDireccion());
            clienteRepository.save(usuarioAdmin);

            UsuarioCliente admin = new UsuarioCliente();
            admin.setIsAdmin(true);
            admin.setUsuario(data.getUsername());
            admin.setDpi(data.getDpi());
            admin.setPasswordHash(passwordEncoder.encode(data.getPassword()));
            usuarioClienteRepository.save(admin);
        }
    }
}
