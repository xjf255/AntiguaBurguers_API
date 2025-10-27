package com.project.antiguaburguers.service;

import com.project.antiguaburguers.model.Combo;
import com.project.antiguaburguers.model.Promocion;
import com.project.antiguaburguers.repository.ComboRepository;
import com.project.antiguaburguers.repository.PromocionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class PromocionService {

    private final PromocionRepository promoRepo;
    private final ComboRepository comboRepo;

    public PromocionService(PromocionRepository promoRepo, ComboRepository comboRepo) {
        this.promoRepo = promoRepo;
        this.comboRepo = comboRepo;
    }

    @Transactional
    public Promocion crear(Promocion p) {
        validarDescuento(p.getDescuento());
        // si viene combo, valida que exista
        if (p.getCombo() != null) {
            Combo combo = comboRepo.findById(p.getCombo().getNumCombo())
                    .orElseThrow(() -> new EntityNotFoundException("Combo no existe: " + p.getCombo().getNumCombo()));
            p.setCombo(combo);
        }
        return promoRepo.save(p); // numPromocion lo genera la BD
    }

    @Transactional
    public Promocion actualizar(String numPromocion, Promocion body) {
        Promocion existente = promoRepo.findById(numPromocion)
                .orElseThrow(() -> new EntityNotFoundException("Promoción no encontrada: " + numPromocion));

        validarDescuento(body.getDescuento());
        existente.setDescuento(body.getDescuento());
        existente.setFechaInicio(body.getFechaInicio());
        existente.setFechaFin(body.getFechaFin());
        existente.setDescripcion(body.getDescripcion());

        if (body.getCombo() != null) {
            Combo combo = comboRepo.findById(body.getCombo().getNumCombo())
                    .orElseThrow(() -> new EntityNotFoundException("Combo no existe: " + body.getCombo().getNumCombo()));
            existente.setCombo(combo);
        } else {
            existente.setCombo(null);
        }

        return promoRepo.save(existente);
    }

    @Transactional
    public void eliminar(String numPromocion) {
        if (!promoRepo.existsById(numPromocion))
            throw new EntityNotFoundException("Promoción no encontrada: " + numPromocion);
        promoRepo.deleteById(numPromocion);
    }

    @Transactional
    public List<Promocion> listar() {
        return promoRepo.findAll();
    }

    @Transactional
    public List<Promocion> listarPorCombo(String numCombo) {
        return promoRepo.findByCombo_NumCombo(numCombo);
    }

    @Transactional
    public List<Promocion> listarVigentes(LocalDate fecha) {
        return promoRepo.promocionVigente("Combo",null, fecha);
    }

    @Transactional
    public List<Promocion> listarVigentesPorCombo(String numCombo, LocalDate fecha) {
        return promoRepo.promocionVigente("Combo",numCombo, fecha);
    }

    @Transactional
    public BigDecimal precioVigenteCombo(String numCombo, BigDecimal precioLista, LocalDate fecha) {
        if (precioLista == null) throw new IllegalArgumentException("precioLista requerido");
        var promos = promoRepo.promocionVigente("Combo",numCombo, fecha);
        if (promos.isEmpty()) return precioLista;

        BigDecimal mejor = BigDecimal.ZERO; // mejor % de descuento
        for (var p : promos) {
            if (p.getDescuento() != null && p.getDescuento().compareTo(mejor) > 0) {
                mejor = p.getDescuento();
            }
        }
        // precio - (precio * mejor / 100)
        BigDecimal rebaja = precioLista.multiply(mejor).divide(BigDecimal.valueOf(100));
        BigDecimal finalPrice = precioLista.subtract(rebaja);
        return finalPrice.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : finalPrice;
    }

    private void validarDescuento(BigDecimal d) {
        if (d == null || d.compareTo(BigDecimal.ZERO) < 0 || d.compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new IllegalArgumentException("El descuento debe estar entre 0 y 100");
        }
    }
}
