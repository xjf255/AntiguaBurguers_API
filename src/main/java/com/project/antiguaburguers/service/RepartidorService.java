package com.project.antiguaburguers.service;

import com.project.antiguaburguers.model.Repartidor;
import com.project.antiguaburguers.repository.RepartidorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepartidorService {
    @Autowired
    final RepartidorRepository repartidorRepository;

    RepartidorService(RepartidorRepository repartidorRepository) {
        this.repartidorRepository = repartidorRepository;
    }

    public List<Repartidor> findAll() {
        return repartidorRepository.findAll();
    }

    public Repartidor findById(String dpi) {
        return repartidorRepository.findById(dpi).orElse(null);
    }

    public void deleteRepartidor(Repartidor repartidor) {
        repartidorRepository.delete(repartidor);
    }

    public Repartidor saveRepartidor(Repartidor repartidor) {
        return repartidorRepository.save(repartidor);
    }
}
