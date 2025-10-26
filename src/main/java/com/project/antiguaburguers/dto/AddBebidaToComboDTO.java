package com.project.antiguaburguers.dto;

import com.project.antiguaburguers.model.BebidaId;

public record AddBebidaToComboDTO(String numCombo, BebidaId bebida, int cantidad) { }
