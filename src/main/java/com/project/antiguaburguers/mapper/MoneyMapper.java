package com.project.antiguaburguers.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface MoneyMapper {

    @Named("toMoney")
    default String toMoney(BigDecimal bd) {
        return bd == null ? null : bd.toPlainString();
    }
}
