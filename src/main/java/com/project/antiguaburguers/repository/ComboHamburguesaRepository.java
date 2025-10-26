package com.project.antiguaburguers.repository;

import com.project.antiguaburguers.model.ComboHamburguesa;
import com.project.antiguaburguers.model.ComboHamburguesaId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ComboHamburguesaRepository extends JpaRepository<ComboHamburguesa, ComboHamburguesaId> {
    List<ComboHamburguesa> findAllByComboNumCombo(String comboNumCombo);
    Optional<ComboHamburguesa> findByComboNumComboAndNombreHamburguesa(String numCombo, String nombreHamburguesa);
}
