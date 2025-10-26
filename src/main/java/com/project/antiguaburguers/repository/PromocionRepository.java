package com.project.antiguaburguers.repository;

import com.project.antiguaburguers.model.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PromocionRepository extends JpaRepository<Promocion, String> {
    List<Promocion> promocionVigente(@Param("tipo") String tipo,
                                     @Param("itemId") String itemId,
                                     @Param("fecha") LocalDate fecha);
}
