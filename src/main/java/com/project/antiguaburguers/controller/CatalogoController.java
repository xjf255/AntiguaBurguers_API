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

        Stream<ItemCatalogoDTO> hamburguesas = menuService.hambActivas().stream().map(h ->
                new ItemCatalogoDTO(
                        "HAMBURGUESA",
                        h.getNombre(),
                        h.getCosto(),
                        precioService.precioVigenteIndividual("HAMBURGUESA", String.valueOf(h.getNombre()), h.getCostoCombo(), hoy),
                        h.isExistencia()
                )
        );

        Stream<ItemCatalogoDTO> bebidas = menuService.bebidasActivas().stream().map(b ->
                new ItemCatalogoDTO(
                        "BEBIDA",
                        b.getNombre(),
                        b.getCosto(),
                        precioService.precioVigenteIndividual("BEBIDA", b.getNombre(), b.getCostoCombo(), hoy),
                        b.getExistencia()
                )
        );

        Stream<ItemCatalogoDTO> complementos = menuService.complActivos().stream().map(c ->
                new ItemCatalogoDTO(
                        "COMPLEMENTO",
                        c.getNombre(),
                        c.getCosto(),
                        precioService.precioVigenteIndividual("COMPLEMENTO", String.valueOf(c.getNombre()), c.getCostoCombo(), hoy),
                        c.getExistencia()
                )
        );

        Stream<ItemCatalogoDTO> combos = menuService.listarCombosActivos().stream().map(cb -> {
            BigDecimal precioLista = cb.getPrecio(); // o calcula sumando componentes si así lo definiste
            BigDecimal vigente = precioService.precioVigenteIndividual("COMBO", String.valueOf(cb.getId()), precioLista, hoy);
            return new ItemCatalogoDTO(
                    "COMBO",
                    cb.getNombre(),
                    precioLista,
                    cb.isActivo()
            );
        });

        Stream<ItemCatalogoDTO> union = Stream.of(hamburguesas, bebidas, complementos, combos)
                .flatMap(s -> s);

        if (tipo == null) return union.toList();
        return union.filter(it -> it.tipo().equalsIgnoreCase(tipo)).toList();
    }
}

