package com.project.antiguaburguers.controller;

import com.project.antiguaburguers.dto.*;
import com.project.antiguaburguers.model.Combo;
import com.project.antiguaburguers.service.MenuService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/combos")
public class ComboController {
    private final MenuService menuService;

    public ComboController(MenuService menuService) { this.menuService = menuService; }

    @PostMapping
    public Combo crear(@RequestBody CreateCombo cmd) {
        return menuService.crearCombo(cmd);
    }

    @PostMapping("/{numCombo}/hamburguesas")
    public Combo addHamburguesa(@PathVariable String numCombo, @RequestBody AddHamburguesaToComboDTO body) {
        return menuService.agregarHamburguesa(new AddHamburguesaToComboDTO(numCombo, body.hamburguesa()));
    }

    @PutMapping("/{numCombo}")
    public ResponseEntity<?> actualizarCombo(@PathVariable String numCombo, @RequestBody CreateCombo dto) {
        try {
            Combo actualizado = menuService.actualizarCombo(numCombo, dto);
            return ResponseEntity.ok(actualizado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{numCombo}/bebidas")
    public Combo addBebida(@PathVariable String numCombo, @RequestBody AddBebidaToComboDTO body) {
        return menuService.agregarBebida(new AddBebidaToComboDTO(numCombo,body.bebida()));
    }

    @PostMapping("/{numCombo}/complementos")
    public Combo addComplemento(@PathVariable String numCombo, @RequestBody AddComplementoToComboDTO body) {
        return menuService.agregarComplemento(new AddComplementoToComboDTO(numCombo, body.complemento()));
    }

    @DeleteMapping("/{numCombo}/hamburguesas/{hambId}")
    public void quitarHamb(@PathVariable String numCombo, @PathVariable String hambId) {
        menuService.quitarHamburguesa(numCombo, hambId);
    }

    @GetMapping
    public List<ComboDetailDTO> listarCombosConDetalle() {
        return menuService.listarCombosConDetalle();
    }

    @GetMapping("/{numCombo}")
    public ResponseEntity<?> obtenerComboConDetalle(@PathVariable String numCombo) {
        try {
            return ResponseEntity.ok(menuService.getComboConDetalle(numCombo));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{numCombo}")
    public ResponseEntity<?> eliminarCombo(@PathVariable String numCombo) {
        try {
            menuService.eliminarComboCompleto(numCombo);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(409).body(
                    Map.of("error", "No se puede eliminar el combo, posiblemente está referenciado en otra entidad.")
            );
        }
    }

}
