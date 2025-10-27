package com.project.antiguaburguers.repository;

import com.project.antiguaburguers.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ComboBebidaIdRepository extends JpaRepository<ComboBebida, ComboBebidaId> {

    Optional<ComboBebida> findByComboAndBebida(Combo combo, Bebida bebida);

    List<ComboBebida> findAllByCombo_NumCombo(String numCombo);

    void deleteByComboAndBebida(Combo combo, Bebida bebida);
}

