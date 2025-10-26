package com.project.antiguaburguers.service;

import com.project.antiguaburguers.model.Hamburguesa;
import com.project.antiguaburguers.repository.HamburguesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HamburguesaService {
    @Autowired
    final HamburguesaRepository hamburguesaRepository;

    HamburguesaService(HamburguesaRepository hamburguesaRepository) {
        this.hamburguesaRepository = hamburguesaRepository;
    }

    public List<Hamburguesa> getAllHamburguesas() {
        return hamburguesaRepository.findAll();
    }

    public Hamburguesa getHamburguesa(String name) {
        return hamburguesaRepository.findById(name).orElse(null);
    }

    public void deleteHamburguesa(String name) {
        hamburguesaRepository.deleteById(name);
    }

    public Hamburguesa saveHamburguesa(Hamburguesa hamburguesa) {
        return hamburguesaRepository.save(hamburguesa);
    }
}
