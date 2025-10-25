package com.project.antiguaburguers.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@MapperConfig(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR, // te avisa si olvidaste mapear algo
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface CentralMapperConfig {}
