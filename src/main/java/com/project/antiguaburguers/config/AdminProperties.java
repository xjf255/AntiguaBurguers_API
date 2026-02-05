package com.project.antiguaburguers.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "admin")
@Data
public class AdminProperties {
    private String username;
    private String password;
    private String nombre;
    private String apellido;
    private String email;
    private String dpi;
    private String telefono;
    private String prefijoTelefono;
    private String direccion;
}