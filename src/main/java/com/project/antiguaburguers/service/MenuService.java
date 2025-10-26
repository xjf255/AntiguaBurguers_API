package com.project.antiguaburguers.service;

import com.project.antiguaburguers.dto.*;
import com.project.antiguaburguers.model.*;
import com.project.antiguaburguers.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MenuService {
    private final HamburguesaRepository hambRepo;
    private final BebidaRepository bebidaRepo;
    private final ComplementoRepository compRepo;
    private final ComboRepository comboRepo;
    private final ComboHamburguesaRepository chRepo;
    private final ComboBebidaIdRepository cbRepo;
    private final ComboComplementoRepository ccRepo;

    public MenuService(HamburguesaRepository hambRepo, BebidaRepository bebidaRepo,
                       ComplementoRepository compRepo, ComboRepository comboRepo,
                       ComboHamburguesaRepository chRepo,
                       ComboBebidaIdRepository cbRepo,
                       ComboComplementoRepository ccRepo) {
        this.hambRepo = hambRepo;
        this.bebidaRepo = bebidaRepo;
        this.compRepo = compRepo;
        this.comboRepo = comboRepo;
        this.chRepo = chRepo; this.cbRepo = cbRepo; this.ccRepo = ccRepo;
    }

    @Transactional
    public Hamburguesa crearHamburguesa(CreateHamburguesaDTO cmd) {
        Hamburguesa hamburguesa = new Hamburguesa();
        hamburguesa
    }
    @Transactional
    public Bebida crearBebida(CreateBebidaDTO cmd) { ... }
    @Transactional
    public Complemento crearComplemento(CreateComplementoDTO cmd) { ... }

    @Transactional
    public Hamburguesa cambiarPrecioHamb(Long id, BigDecimal precio) { ... }
    @Transactional
    public Bebida cambiarPrecioBebida(BebidaId id, BigDecimal precio) { ... }
    @Transactional
    public Complemento cambiarPrecioCompl(Long id, BigDecimal precio) { ... }

    @Transactional
    public Hamburguesa activarHamb(Long id, boolean activo) { ... }
    @Transactional
    public Bebida activarBebida(BebidaId id, boolean activo) { ... }
    @Transactional
    public Complemento activarCompl(Long id, boolean activo) { ... }

    @Transactional
    public List<Hamburguesa> hambActivas() { return hambRepo.findByExistenciaTrue(); }
    @Transactional
    public List<Bebida> bebidasActivas() { return bebidaRepo.findByExistenciaTrue(); }
    @Transactional
    public List<Complemento> complActivos() { return compRepo.findByExistenciaTrue(); }

    @Transactional
    public Combo crearCombo(CreateCombo cmd) {
        var c = new Combo();
        c.setNombre(cmd.nombre());
        c.setDescripcion(cmd.descripcion());
        c.setCosto(cmd.costo());
        c.setExistencia(cmd.existencia());
        return comboRepo.save(c);
    }

    @Transactional public Combo activarCombo(Long comboId, boolean activo) { ... }

    @Transactional
    public Combo agregarHamburguesa(AddHamburguesaToComboDTO cmd) {
        var combo = comboRepo.findById(cmd.numCombo())
                .orElseThrow(() -> new EntityNotFoundException("Combo " + cmd.numCombo() + " no existe"));
        var hamb = hambRepo.findById(cmd.numCombo())
                .orElseThrow(() -> new EntityNotFoundException("Hamburguesa " + cmd.numCombo()));

        var existente = chRepo.findByComboNumComboAndNombreHamburguesa(combo.getNumCombo(), hamb.getNombre()).orElse(null);
        if (existente == null) {
            var ch = new ComboHamburguesa();
            ch.setCombo(combo);
            ch.setNombreHamburguesa(hamb);
            chRepo.save(ch);
        } else {
            chRepo.save(existente);
        }
        return combo;
    }

    @Transactional
    public Combo agregarBebida(AddBebidaToComboDTO cmd) { ... }  // similar usando BebidaId
    @Transactional
    public Combo agregarComplemento(AddComplementoToComboDTO cmd) { ... }

    @Transactional
    public void quitarHamburguesa(Long comboId, Long hambId) { ... }
    @Transactional
    public void quitarBebida(Long comboId, BebidaId bebidaId) { ... }
    @Transactional
    public void quitarComplemento(Long comboId, Long compId) { ... }

    @Transactional(readOnly = true)
    public List<ComboHamburguesa> hamburguesasDeCombo(String comboId) { return chRepo.findAllByComboNumCombo(comboId); }
    @Transactional(readOnly = true)
    public List<ComboBebida> bebidasDeCombo(String comboId) { return cbRepo.findAllByCombo_NumCombo(comboId); }
    @Transactional(readOnly = true)
    public List<ComboComplemento> complementosDeCombo(String comboId) { return ccRepo.findAllByCombo_NumCombo(comboId); }
}
