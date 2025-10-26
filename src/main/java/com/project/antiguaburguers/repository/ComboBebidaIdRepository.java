package com.project.antiguaburguers.repository;

import com.project.antiguaburguers.model.ComboBebida;
import com.project.antiguaburguers.model.ComboBebidaId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComboBebidaIdRepository extends JpaRepository<ComboBebida, ComboBebidaId> {
    List<ComboBebida> findAllByCombo_NumCombo(String numCombo);
}
