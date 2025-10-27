package com.project.antiguaburguers.controller;

import com.project.antiguaburguers.dto.ItemCatalogoDTO;
import com.project.antiguaburguers.service.MenuService;
import com.project.antiguaburguers.service.PrecioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/catalogo")
public class CatalogoController {
    private final MenuService menuService;
    private final PrecioService precioService;

    public CatalogoController(MenuService menuService, PrecioService precioService) {
        this.menuService = menuService;
        this.precioService = precioService;
    }

    @GetMapping("/items")
    public List<ItemCatalogoDTO> listarCatalogo(@RequestParam(required = false) String tipo) {
        var hoy = LocalDate.now();

        Stream<ItemCatalogoDTO> hamburguesas = menuService.listarHamburguesasDisponibles().stream().map(h -> {
            BigDecimal precioLista = h.getCosto(); // base para individual
            BigDecimal vigente = precioService.precioVigenteIndividual(
                    "HAMBURGUESA",
                    h.getNombre(),
                    precioLista,
                    hoy
            );
            return new ItemCatalogoDTO(
                    "HAMBURGUESA",
                    h.getNombre(),     // ID legible
                    precioLista,
                    vigente,
                    h.isExistencia()
            );
        });

        Stream<ItemCatalogoDTO> bebidas = menuService.listarBebidasDisponibles().stream().map(b -> {
            String bebidaIdStr = b.getNombre() + "|" + b.getCantidad(); // ID compuesto en string
            BigDecimal precioLista = b.getCosto();
            BigDecimal vigente = precioService.precioVigenteIndividual(
                    "BEBIDA",
                    bebidaIdStr,
                    precioLista,
                    hoy
            );
            return new ItemCatalogoDTO(
                    "BEBIDA",
                    bebidaIdStr,
                    precioLista,
                    vigente,
                    Boolean.TRUE.equals(b.getExistencia())
            );
        });

        Stream<ItemCatalogoDTO> complementos = menuService.listarComplementosDisponibles().stream().map(c -> {
            BigDecimal precioLista = c.getCosto();
            BigDecimal vigente = precioService.precioVigenteIndividual(
                    "COMPLEMENTO",
                    c.getNombre(),
                    precioLista,
                    hoy
            );
            return new ItemCatalogoDTO(
                    "COMPLEMENTO",
                    c.getNombre(),
                    precioLista,
                    vigente,
                    Boolean.TRUE.equals(c.getExistencia())
            );
        });

        Stream<ItemCatalogoDTO> combos = menuService.listarCombosDisponibles().stream().map(cb -> {
            BigDecimal precioLista = cb.getCosto(); // si calculas por componentes, cámbialo
            BigDecimal vigente = precioService.precioVigenteIndividual(
                    "COMBO",
                    String.valueOf(cb.getNumCombo()),
                    precioLista,
                    hoy
            );
            return new ItemCatalogoDTO(
                    "COMBO",
                    String.valueOf(cb.getNumCombo()),
                    precioLista,
                    vigente,
                    cb.getExistencia()
            );
        });

        Stream<ItemCatalogoDTO> union = Stream.of(hamburguesas, bebidas, complementos, combos).flatMap(s -> s);
        if (tipo == null) return union.toList();
        return union.filter(it -> it.tipo().equalsIgnoreCase(tipo)).toList();
    }
}

