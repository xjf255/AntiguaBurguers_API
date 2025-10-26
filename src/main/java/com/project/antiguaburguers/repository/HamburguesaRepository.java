package com.project.antiguaburguers.repository;

import com.project.antiguaburguers.model.Hamburguesa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HamburguesaRepository extends JpaRepository<Hamburguesa, String> {
    List<Hamburguesa> findByNombreContaining(String name);
    List<Hamburguesa> findByExistenciaTrue();
}
