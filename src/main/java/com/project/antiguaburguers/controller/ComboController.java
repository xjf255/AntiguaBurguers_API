package com.project.antiguaburguers.controller;

import com.project.antiguaburguers.dto.AddBebidaToComboDTO;
import com.project.antiguaburguers.dto.AddComplementoToComboDTO;
import com.project.antiguaburguers.dto.AddHamburguesaToComboDTO;
import com.project.antiguaburguers.dto.CreateCombo;
import com.project.antiguaburguers.model.Combo;
import com.project.antiguaburguers.service.MenuService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/combos")
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
}
