package com.project.antiguaburguers.repository;

import com.project.antiguaburguers.model.Hamburguesa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HamburguesaRepository extends JpaRepository<Hamburguesa, String> {
}
