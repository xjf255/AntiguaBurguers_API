package com.project.antiguaburguers.repository;

import com.project.antiguaburguers.model.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PromocionRepository extends JpaRepository<Promocion, String> {
    List<Promocion> findByCombo_NumCombo(String numCombo);

    @Query("""
       select p
       from Promocion p
       where (:numCombo is null or p.combo.numCombo = :numCombo)
         and (:fecha    is null or
              (coalesce(p.fechaInicio, :fecha) <= :fecha
           and  coalesce(p.fechaFin,    :fecha) >= :fecha))
    """)
    List<Promocion> promocionVigente(
            @Param("numCombo") String numCombo,
            @Param("fecha") LocalDate fecha
    );
}
