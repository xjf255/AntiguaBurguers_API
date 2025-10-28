package com.project.antiguaburguers.service;

import com.project.antiguaburguers.dto.PromocionDTO;
import com.project.antiguaburguers.model.Combo;
import com.project.antiguaburguers.model.Promocion;
import com.project.antiguaburguers.repository.ComboRepository;
import com.project.antiguaburguers.repository.PromocionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromocionService {

    private final PromocionRepository promoRepo;
    private final ComboRepository comboRepo;
    private final PromocionRepository promocionRepository;
    private final ComboRepository comboRepository;

    public PromocionService(PromocionRepository promoRepo, ComboRepository comboRepo, PromocionRepository promocionRepository, ComboRepository comboRepository) {
        this.promoRepo = promoRepo;
        this.comboRepo = comboRepo;
        this.promocionRepository = promocionRepository;
        this.comboRepository = comboRepository;
    }

    @Transactional
    public List<PromocionDTO> listarTodas() {
        return promocionRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<PromocionDTO> listarVigentes() {
        LocalDate hoy = LocalDate.now();
        return promocionRepository.findAll().stream()
                .filter(p ->
                        (p.getFechaInicio() == null || !hoy.isBefore(p.getFechaInicio())) &&
                                (p.getFechaFin() == null || !hoy.isAfter(p.getFechaFin()))
                )
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private PromocionDTO toDTO(Promocion p) {
        return new PromocionDTO(
                p.getNumPromocion(),
                p.getCombo() != null ? p.getCombo().getNumCombo() : null,
                p.getCombo() != null ? p.getCombo().getImg() : null,
                p.getDescuento(),
                p.getFechaInicio(),
                p.getFechaFin(),
                p.getDescripcion(),
                p.getCombo() != null ? calcularTotal(p.getCombo().getCosto(), p.getDescuento()) : null,
                calculoDiasDisponible(LocalDateTime.now(), p.getFechaFin().atTime(23, 59, 59))
        );
    }
    private void validarDescuento(BigDecimal d) {
        if (d == null || d.compareTo(BigDecimal.ZERO) < 0 || d.compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new IllegalArgumentException("El descuento debe estar entre 0 y 100");
        }
    }

    // POST: crear promoción
    @Transactional
    public PromocionDTO crearPromocion(PromocionDTO dto) {
        var combo = comboRepository.findById(dto.numCombo())
                .orElseThrow(() -> new EntityNotFoundException("Combo no encontrado: " + dto.numCombo()));

        var promo = new Promocion();
        promo.setCombo(combo);
        promo.setDescuento(dto.descuento());
        promo.setFechaInicio(dto.fechaInicio());
        promo.setFechaFin(dto.fechaFin());
        promo.setDescripcion(dto.descripcion());

        promocionRepository.save(promo);

        return new PromocionDTO(
                promo.getNumPromocion(),
                combo.getNumCombo(),
                combo.getImg(),
                promo.getDescuento(),
                promo.getFechaInicio(),
                promo.getFechaFin(),
                promo.getDescripcion(),
                promo.getCombo() != null ? calcularTotal(promo.getCombo().getCosto(), promo.getDescuento()) : null,
                calculoDiasDisponible(LocalDateTime.now(), promo.getFechaFin().atTime(23, 59, 59))
        );
    }

    // PUT: actualizar promoción
    @Transactional
    public PromocionDTO actualizarPromocion(String numPromocion, PromocionDTO dto) {
        var promo = promocionRepository.findById(numPromocion)
                .orElseThrow(() -> new EntityNotFoundException("Promoción no encontrada: " + numPromocion));

        if (dto.numCombo() != null) {
            var combo = comboRepository.findById(dto.numCombo())
                    .orElseThrow(() -> new EntityNotFoundException("Combo no encontrado: " + dto.numCombo()));
            promo.setCombo(combo);
        }

        promo.setDescuento(dto.descuento());
        promo.setFechaInicio(dto.fechaInicio());
        promo.setFechaFin(dto.fechaFin());
        promo.setDescripcion(dto.descripcion());

        promocionRepository.save(promo);

        return new PromocionDTO(
                promo.getNumPromocion(),
                promo.getCombo() != null ? promo.getCombo().getNumCombo() : null,
                promo.getCombo() != null ? promo.getCombo().getImg() : null,
                promo.getDescuento(),
                promo.getFechaInicio(),
                promo.getFechaFin(),
                promo.getDescripcion(),
                promo.getCombo() != null ? calcularTotal(promo.getCombo().getCosto(), promo.getDescuento()) : null,
                calculoDiasDisponible(LocalDateTime.now(), promo.getFechaFin().atTime(23, 59, 59))
        );
    }

    // DELETE: eliminar promoción
    @Transactional
    public void eliminarPromocion(String numPromocion) {
        if (!promocionRepository.existsById(numPromocion))
            throw new EntityNotFoundException("Promoción no encontrada: " + numPromocion);

        promocionRepository.deleteById(numPromocion);
    }

    private BigDecimal calcularTotal(BigDecimal costo, BigDecimal descuento){
        BigDecimal totalDescuento = costo.multiply(descuento)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        return costo.subtract(totalDescuento);
    }

    private String calculoDiasDisponible(LocalDateTime inicio, LocalDateTime fin) {
        Duration duration = Duration.between(inicio, fin);

        long dias = duration.toDays();
        long horas = duration.toHoursPart();
        long minutos = duration.toMinutesPart();

        return dias + " días, " + horas + " horas, " + minutos + " minutos";
    }
}
