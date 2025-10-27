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
      select p from Promocion p
      where (:numCombo is null or p.combo.numCombo = :numCombo)
        and (p.fechaInicio is null or p.fechaInicio <= :fecha)
        and (p.fechaFin    is null or p.fechaFin    >= :fecha)
    """)
    List<Promocion> promocionVigente(@Param("tipo") String tipo,
                                     @Param("itemId") String itemId,
                                     @Param("fecha") LocalDate fecha);
}
