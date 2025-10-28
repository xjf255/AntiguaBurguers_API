package com.project.antiguaburguers.service;

import com.project.antiguaburguers.repository.PromocionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class PrecioService {

    private final PromocionRepository promoRepo;

    public PrecioService(PromocionRepository promoRepo) { this.promoRepo = promoRepo; }

    public BigDecimal precioVigenteIndividual(String tipo, BigDecimal precioLista, LocalDate fecha) {
        var promos = promoRepo.promocionVigente(tipo, fecha);
        if (promos.isEmpty()) return precioLista;

        BigDecimal mejor = precioLista;
        for (var p : promos) {
            BigDecimal aplicado = precioLista.subtract(precioLista.multiply(p.getCombo().getCosto()).movePointLeft(2));;
            if (aplicado.compareTo(mejor) < 0) mejor = aplicado;
        }
        return mejor.max(BigDecimal.ZERO);
    }
}
