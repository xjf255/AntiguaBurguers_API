package com.project.antiguaburguers.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "usuario_cliente")
public class UsuarioCliente {

    @Id
    @Column(name = "usuario", nullable = false)
    private String usuario;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "dpi", length = 13, nullable = false)
    private String dpi;

    @Column(name = "is_admin", nullable = false)
    private Boolean isAdmin;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dpi", referencedColumnName = "dpi", insertable = false, updatable = false)
    private Cliente dpiCliente;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
