package com.project.antiguaburguers.repository;

import com.project.antiguaburguers.model.ComboComplemento;
import com.project.antiguaburguers.model.ComboComplementoId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComboComplementoRepository extends JpaRepository<ComboComplemento, ComboComplementoId> {
    List<ComboComplemento> findAllByCombo_NumCombo(String combo);
}
