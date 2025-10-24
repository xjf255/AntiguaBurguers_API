package com.project.antiguaburguers.repository;

import com.project.antiguaburguers.model.ComboHamburguesa;
import com.project.antiguaburguers.model.ComboHamburguesaId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComboHamburguesaRepository extends JpaRepository<ComboHamburguesa, ComboHamburguesaId> {
}
