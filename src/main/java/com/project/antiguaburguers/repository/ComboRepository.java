package com.project.antiguaburguers.repository;

import com.project.antiguaburguers.model.Combo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ComboRepository extends JpaRepository<Combo, String> {
    List<Combo> findComboByExistenciaTrue();

    @Query("""
        SELECT DISTINCT c FROM Combo c
        LEFT JOIN FETCH c.hamburguesas ch
        LEFT JOIN FETCH ch.nombreHamburguesa
        WHERE c.existencia = true
    """)
    List<Combo> findCombosConHamburguesasDisponibles();
}
