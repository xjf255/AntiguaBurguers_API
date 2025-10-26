package com.project.antiguaburguers.repository;

import com.project.antiguaburguers.model.Combo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComboRepository extends JpaRepository<Combo, String> {
    List<Combo> findComboByExistenciaTrue();
}
