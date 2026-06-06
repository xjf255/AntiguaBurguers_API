package com.project.antiguaburguers.repository;

import com.project.antiguaburguers.model.EstadoEntrega;
import com.project.antiguaburguers.utils.EstadoEntregaEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoEntregaRepository extends JpaRepository<EstadoEntrega, EstadoEntregaEnum> {
}
