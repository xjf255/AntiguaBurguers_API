package com.project.antiguaburguers.repository;

import com.project.antiguaburguers.model.Combo;
import com.project.antiguaburguers.model.ComboHamburguesa;
import com.project.antiguaburguers.model.ComboHamburguesaId;
import com.project.antiguaburguers.model.Hamburguesa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ComboHamburguesaRepository extends JpaRepository<ComboHamburguesa, ComboHamburguesaId> {

    Optional<ComboHamburguesa> findByComboAndNombreHamburguesa(Combo combo, Hamburguesa nombreHamburguesa);

    List<ComboHamburguesa> findAllByCombo_NumCombo(String numCombo);

    void deleteByComboAndNombreHamburguesa(Combo combo, Hamburguesa nombreHamburguesa);
}

