package com.project.antiguaburguers.repository;

import com.project.antiguaburguers.model.Combo;
import com.project.antiguaburguers.model.ComboComplemento;
import com.project.antiguaburguers.model.ComboComplementoId;
import com.project.antiguaburguers.model.Complemento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ComboComplementoRepository extends JpaRepository<ComboComplemento, ComboComplementoId> {

    Optional<ComboComplemento> findByComboAndComplemento(Combo combo, Complemento complemento);

    List<ComboComplemento> findAllByCombo_NumCombo(String numCombo);

    void deleteByComboAndComplemento(Combo combo, Complemento complemento);
}

