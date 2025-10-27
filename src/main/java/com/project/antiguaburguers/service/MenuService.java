package com.project.antiguaburguers.service;

import com.project.antiguaburguers.dto.*;
import com.project.antiguaburguers.model.*;
import com.project.antiguaburguers.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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

    public MenuService(HamburguesaRepository hambRepo,
                       BebidaRepository bebidaRepo,
                       ComplementoRepository compRepo,
                       ComboRepository comboRepo,
                       ComboHamburguesaRepository chRepo,
                       ComboBebidaIdRepository cbRepo,
                       ComboComplementoRepository ccRepo) {
        this.hambRepo = hambRepo;
        this.bebidaRepo = bebidaRepo;
        this.compRepo = compRepo;
        this.comboRepo = comboRepo;
        this.chRepo = chRepo;
        this.cbRepo = cbRepo;
        this.ccRepo = ccRepo;
    }

    @Transactional
    public List<Hamburguesa> listarHamburguesasDisponibles() {
        return hambRepo.findByExistenciaTrue();
    }

    @Transactional
    public List<Combo> listarCombosDisponibles() {
        return comboRepo.findComboByExistenciaTrue();
    }

    @Transactional
    public List<Bebida> listarBebidasDisponibles() {
        return bebidaRepo.findByExistenciaTrue();
    }

    @Transactional
    public List<Complemento> listarComplementosDisponibles() {
        return compRepo.findByExistenciaTrue();
    }

    @Transactional
    public Hamburguesa crearHamburguesa(CreateHamburguesaDTO cmd) {
        var h = new Hamburguesa();
        h.setNombre(cmd.nombre());
        h.setCosto(cmd.costo());
        h.setCostoCombo(cmd.costoCombo());
        h.setImg(cmd.img());
        h.setExistencia(cmd.existencia());
        return hambRepo.save(h);
    }

    @Transactional
    public Bebida crearBebida(CreateBebidaDTO cmd) {
        var b = new Bebida();
        b.setNombre(cmd.nombre());
        b.setCosto(cmd.costo());
        b.setImg(cmd.img());
        b.setExistencia(cmd.existencia());
        return bebidaRepo.save(b);
    }

    @Transactional
    public Complemento crearComplemento(CreateComplementoDTO cmd) {
        var c = new Complemento();
        c.setNombre(cmd.nombre());
        c.setCosto(cmd.costo());
        c.setImg(cmd.img());
        c.setExistencia(cmd.existencia());
        return compRepo.save(c);
    }

    @Transactional
    public Hamburguesa cambiarPrecioHamb(String id, BigDecimal precio) {
        var h = hambRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hamburguesa no encontrada: " + id));
        h.setCosto(precio);
        return hambRepo.save(h);
    }

    @Transactional
    public Bebida cambiarPrecioBebida(BebidaId id, BigDecimal precio) {
        var b = bebidaRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bebida no encontrada: " + id));
        b.setCosto(precio);
        return bebidaRepo.save(b);
    }

    @Transactional
    public Complemento cambiarPrecioCompl(String id, BigDecimal precio) {
        var c = compRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Complemento no encontrado: " + id));
        c.setCosto(precio);
        return compRepo.save(c);
    }

    @Transactional
    public Hamburguesa activarHamb(String id, boolean activo) {
        var h = hambRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hamburguesa no encontrada: " + id));
        h.setExistencia(activo);
        return hambRepo.save(h);
    }

    @Transactional
    public Bebida activarBebida(BebidaId id, boolean activo) {
        var b = bebidaRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bebida no encontrada: " + id));
        b.setExistencia(activo);
        return bebidaRepo.save(b);
    }

    @Transactional
    public Complemento activarCompl(String id, boolean activo) {
        var c = compRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Complemento no encontrado: " + id));
        c.setExistencia(activo);
        return compRepo.save(c);
    }

    @Transactional
    public Combo crearCombo(CreateCombo cmd) {
        var c = new Combo();
        c.setNombre(cmd.nombre());
        c.setDescripcion(cmd.descripcion());
        c.setCosto(cmd.costo());
        c.setExistencia(cmd.existencia());
        c.setImg(cmd.img());
        return comboRepo.save(c);
    }

    @Transactional
    public Combo activarCombo(String comboId, boolean activo) {
        var c = comboRepo.findById(comboId)
                .orElseThrow(() -> new EntityNotFoundException("Combo no encontrado: " + comboId));
        c.setExistencia(activo);
        return comboRepo.save(c);
    }

    @Transactional
    public Combo agregarHamburguesa(AddHamburguesaToComboDTO cmd) {
        var combo = comboRepo.findById(cmd.numCombo())
                .orElseThrow(() -> new EntityNotFoundException("Combo " + cmd.numCombo() + " no existe"));
        var hamb = hambRepo.findById(cmd.hamburguesa())
                .orElseThrow(() -> new EntityNotFoundException("Hamburguesa " + cmd.hamburguesa() + " no existe"));

        var existente = chRepo.findByComboAndNombreHamburguesa(combo, hamb).orElse(null);
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
    public Combo agregarBebida(AddBebidaToComboDTO cmd) {
        var combo = comboRepo.findById(cmd.numCombo())
                .orElseThrow(() -> new EntityNotFoundException("Combo " + cmd.numCombo() + " no existe"));
        var bebida = bebidaRepo.findById(cmd.bebida())
                .orElseThrow(() -> new EntityNotFoundException("Bebida no encontrada: " + cmd.bebida()));

        var existente = cbRepo.findByComboAndBebida(combo,bebida).orElse(null);
        if (existente == null) {
            var cb = new ComboBebida();
            cb.setCombo(combo);
            cb.setBebida(bebida);
            cbRepo.save(cb);
        } else {
            cbRepo.save(existente);
        }
        return combo;
    }

    @Transactional
    public Combo agregarComplemento(AddComplementoToComboDTO cmd) {
        var combo = comboRepo.findById(cmd.comboId()) // <- antes tenías comboId()
                .orElseThrow(() -> new EntityNotFoundException("Combo " + cmd.comboId() + " no existe"));
        var compl = compRepo.findById(cmd.complemento())
                .orElseThrow(() -> new EntityNotFoundException("Complemento " + cmd.complemento() + " no existe"));

        var existente = ccRepo.findByComboAndComplemento(combo, compl).orElse(null);
        if (existente == null) {
            var cc = new ComboComplemento();
            cc.setCombo(combo);
            cc.setComplemento(compl);
            ccRepo.save(cc);
        } else {
            ccRepo.save(existente);
        }
        return combo;
    }


    @Transactional
    public void quitarHamburguesa(String comboId, String hambId) {
        var combo = comboRepo.findById(comboId)
                .orElseThrow(() -> new EntityNotFoundException("Combo no encontrado: " + comboId));

        var hamb = hambRepo.findById(hambId)
                .orElseThrow(() -> new EntityNotFoundException("Hamburguesa " + hambId));

        var ch = chRepo.findByComboAndNombreHamburguesa(combo, hamb)
                .orElseThrow(() -> new EntityNotFoundException("No existe relación combo-hamburguesa"));

        chRepo.delete(ch);
    }

    @Transactional
    public void quitarBebida(String comboId, BebidaId bebidaId) {
        var combo = comboRepo.findById(comboId)
                .orElseThrow(() -> new EntityNotFoundException("Combo no encontrado: " + comboId));

        var bebida = bebidaRepo.findById(bebidaId)
                .orElseThrow(() -> new EntityNotFoundException("Bebida " + bebidaId));

        var cb = cbRepo.findByComboAndBebida(combo, bebida)
                .orElseThrow(() -> new EntityNotFoundException("No existe relación combo-bebida"));
        cbRepo.delete(cb);
    }

    @Transactional
    public void quitarComplemento(String comboId, String compId) {
        var combo = comboRepo.findById(comboId)
                .orElseThrow(() -> new EntityNotFoundException("Combo no encontrado: " + comboId));
        var comp = compRepo.findById(compId)
                .orElseThrow(() -> new EntityNotFoundException("Complemento " + compId));
        var cc = ccRepo.findByComboAndComplemento(combo, comp)
                .orElseThrow(() -> new EntityNotFoundException("No existe relación combo-complemento"));
        ccRepo.delete(cc);
    }

    @Transactional
    public List<ComboHamburguesa> hamburguesasDeCombo(String comboId) {
        return chRepo.findAllByCombo_NumCombo(comboId);
    }

    @Transactional
    public List<ComboBebida> bebidasDeCombo(String comboId) {
        return cbRepo.findAllByCombo_NumCombo(comboId);
    }

    @Transactional
    public List<ComboComplemento> complementosDeCombo(String comboId) {
        return ccRepo.findAllByCombo_NumCombo(comboId);
    }
}
