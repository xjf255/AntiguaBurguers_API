package com.project.antiguaburguers.repository;

import com.project.antiguaburguers.model.Complemento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplementoRepository extends JpaRepository<Complemento, String> {
    List<Complemento> findByNombreContaining(String name);
    List<Complemento> findByExistenciaTrue();
}
