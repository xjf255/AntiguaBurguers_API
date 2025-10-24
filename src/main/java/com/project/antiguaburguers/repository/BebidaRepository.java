package com.project.antiguaburguers.repository;

import com.project.antiguaburguers.model.Bebida;
import com.project.antiguaburguers.model.BebidaId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BebidaRepository extends JpaRepository<Bebida, BebidaId> {
}
