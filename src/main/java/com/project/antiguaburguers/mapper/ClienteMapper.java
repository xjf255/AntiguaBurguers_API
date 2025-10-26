package com.project.antiguaburguers.mapper;

import com.project.antiguaburguers.model.Cliente;
import com.project.antiguaburguers.dto.ClienteDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
    ClienteDTO toDTO(Cliente cliente);

    Cliente toEntity(ClienteDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ClienteDTO dto, @MappingTarget Cliente entity);

}
