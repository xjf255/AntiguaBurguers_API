package com.project.antiguaburguers.repository;

import com.project.antiguaburguers.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, String> {
}
