package com.project.antiguaburguers.service;

import com.project.antiguaburguers.model.Complemento;
import com.project.antiguaburguers.repository.ComplementoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplementoService {
    @Autowired
    final ComplementoRepository complementoRepository;

    ComplementoService(ComplementoRepository complementoRepository) {
        this.complementoRepository = complementoRepository;
    }

    public List<Complemento> findAll() {
        return complementoRepository.findAll();
    }

    public Complemento findById(String name) {
        return complementoRepository.findById(name).orElse(null);
    }

    public void remove(String name){
        complementoRepository.deleteById(name);
    }

    public Complemento save(Complemento complemento) {
        return complementoRepository.save(complemento);
    }
}
